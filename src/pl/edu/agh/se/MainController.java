package pl.edu.agh.se;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by kkicinger on 2015-12-12.
 */
public class MainController implements Initializable {

    private class ChartEntry implements Comparable<ChartEntry> {

        private final String nodeId;
        private final BigDecimal value;

        ChartEntry(String nodeId, BigDecimal value) {
            this.nodeId = nodeId;
            this.value = value;
        }

        public String getNodeId() {
            return nodeId;
        }

        public BigDecimal getValue() {
            return value;
        }

        @Override
        public int compareTo(ChartEntry o) {
            return getValue().compareTo(o.getValue());
        }

    }

    @FXML
    private Label questionNumberLabel;

    @FXML
    private Label questionLabel;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private PieChart extravertIntrovertPieChart;

    @FXML
    private PieChart neurosisEmotionalBalancePieChart;

    @FXML
    private BarChart<String, Number> personalityTypesBarChart;

    @FXML
    private BarChart<String, Number> additionalPersonalityTypesBarChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCharts();
        updateQuestionLabels();
        updateCharts();
    }

    @FXML
    public void addTrueEvidenceAction(ActionEvent actionEvent) {
        BayesianNetworkService.setEvidenceToTrue(QuestionService.getCurrentQuestion());
        updateValues();
    }

    @FXML
    public void addFalseEvidenceAction(ActionEvent actionEvent) {
        BayesianNetworkService.setEvidenceToFalse(QuestionService.getCurrentQuestion());
        updateValues();
    }

    private void initializeCharts() {
        initializePieChart(extravertIntrovertPieChart);
        initializePieChart(neurosisEmotionalBalancePieChart);
        initializeBarChart(personalityTypesBarChart, "Główne typy osobowości", "Prawdopodobieństwo");
        initializeBarChart(additionalPersonalityTypesBarChart, "Pomocnicze typy osobowości", "Prawdopodobieństwo");
    }

    private void initializePieChart(PieChart pieChart) {
        pieChart.setLabelsVisible(false);
        pieChart.setLegendVisible(true);
        pieChart.setLegendSide(Side.BOTTOM);
    }

    private void initializeBarChart(BarChart<String, Number> barChart, String xAxisLabel, String yAxisLabel) {
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.setLabel(xAxisLabel);
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        yAxis.setLabel(yAxisLabel);
        barChart.setLegendVisible(false);
    }

    private void updateValues() {
        updateQuestion();
        updateCharts();
    }

    private void updateCharts() {
        updateExtravertIntrovertPieChart();
        updateNeurosisEmotionalBalancePieChart();
        updatePersonalityTypesBarChart();
        updateAdditionalPersonalityTypesBarChart();
    }

    private void updateQuestion() {
        QuestionService.nextFeatureQuestion();
        if (QuestionService.isNextFeatureQuestionAvailable()) {
            updateQuestionLabels();
        } else {
            questionNumberLabel.setText("-");
            questionLabel.setText("Odpowiedziałeś na wszystkie pytania.");
            yesButton.setDisable(true);
            noButton.setDisable(true);
        }
    }

    private void updateQuestionLabels() {
        questionNumberLabel.setText("Pytanie " + QuestionService.getCurrentQuestionNumber());
        questionLabel.setText(QuestionService.getCurrentQuestion().getQuestion());
    }

    private void updateExtravertIntrovertPieChart() {
        updatePieChart(extravertIntrovertPieChart, getChartEntriesForNodes(Arrays.asList(BayesianNetworkConfiguration.Global.EXTRAVERT_CHARACTER, BayesianNetworkConfiguration.Global.INTORVERT_CHARACTER)));
    }

    private void updateNeurosisEmotionalBalancePieChart() {
        updatePieChart(neurosisEmotionalBalancePieChart, getChartEntriesForNodes(Arrays.asList(BayesianNetworkConfiguration.Global.NEUROSIS, BayesianNetworkConfiguration.Global.EMOTIONAL_BALANCE)));
    }

    private void updatePersonalityTypesBarChart() {
        updateBarChart(personalityTypesBarChart, getChartEntriesForNodes(Arrays.asList(
                BayesianNetworkConfiguration.Global.SANGUINE,
                BayesianNetworkConfiguration.Global.HOTHEAD,
                BayesianNetworkConfiguration.Global.PHLEGMATIC,
                BayesianNetworkConfiguration.Global.MELANCHOLIC
        )));
    }

    private void updateAdditionalPersonalityTypesBarChart() {
        updateBarChart(additionalPersonalityTypesBarChart, getChartEntriesForNodes(Arrays.asList(
                BayesianNetworkConfiguration.Global.DOM,
                BayesianNetworkConfiguration.Global.DOS,
                BayesianNetworkConfiguration.Global.MIN,
                BayesianNetworkConfiguration.Global.MAK,
                BayesianNetworkConfiguration.Global.ASE,
                BayesianNetworkConfiguration.Global.EMP,
                BayesianNetworkConfiguration.Global.HOJ,
                BayesianNetworkConfiguration.Global.SYS
        )));
    }

    private void updateBarChart(BarChart<String, Number> barChart, List<ChartEntry> nodes) {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>(FXCollections.observableArrayList(nodes.stream().map((node) -> new XYChart.Data<String, Number>(node.getNodeId(), node.getValue().doubleValue())).collect(Collectors.toList())));
        ((CategoryAxis) barChart.getXAxis()).setCategories(FXCollections.observableArrayList(series.getData().stream().map((data) -> data.getXValue()).collect(Collectors.toList())));
        barChart.setAnimated(false);
        barChart.getData().add(series);
    }

    private void updatePieChart(PieChart pieChart, List<ChartEntry> chartEntries) {
        pieChart.getData().clear();
        pieChart.setData(FXCollections.observableArrayList(chartEntries.stream().map((entry) -> new PieChart.Data(entry.getNodeId(), entry.getValue().doubleValue())).collect(Collectors.toList())));
    }

    private List<ChartEntry> getChartEntriesForNodes(List<INameable> nodes) {
        return nodes.stream().map((node) -> createChartEntry(node)).collect(Collectors.toList());
    }

    private ChartEntry createChartEntry(INameable node) {
        return new ChartEntry(node.getName(), BayesianNetworkService.getNodeProbability(node));
    }

}
