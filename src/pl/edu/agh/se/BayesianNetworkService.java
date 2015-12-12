package pl.edu.agh.se;

import smile.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by kkicinger on 2015-12-12.
 */
public class BayesianNetworkService {

    private static final String BAYESIAN_NETWORK_FILE_NAME = "BayesianNetwork.xdsl";
    private static final String TRUE_STATE = "True";
    private static final String FALSE_STATE = "False";
    private static final int PROBABILITY_SCALE = 4;

    private static Network network;

    public static void initializeNetwork() {
        network = new Network();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(BayesianNetworkService.class.getClassLoader().getResourceAsStream(BAYESIAN_NETWORK_FILE_NAME)))) {
            String xdslContent = reader.lines().collect(Collectors.toList()).stream().reduce((s1, s2) -> s1 + s2).get();
            network.readString(xdslContent);
        } catch (IOException ex) {
            System.out.println("LOG: Network not properly initialized.");
        }
    }

    public static List<String> getAllNodeIds() {
        return Arrays.asList(network.getAllNodeIds());
    }

    public static BigDecimal getNodeProbability(INode nodeId) {
        return getTrueStateProbability(nodeId.getNodeId());
    }

    public static void setEvidenceToTrue(BayesianNetworkConfiguration.Features feature) {
        setEvidence(feature, TRUE_STATE);
        updateBeliefs();
    }

    public static void setEvidenceToFalse(BayesianNetworkConfiguration.Features feature) {
        setEvidence(feature, FALSE_STATE);
        updateBeliefs();
    }

    public static void updateBeliefs() {
        network.updateBeliefs();
    }

    public static void clear() {
        network.clearAllEvidence();
        network.clearAllTargets();
        updateBeliefs();
    }

    private static void setEvidence(BayesianNetworkConfiguration.Features feature, String evidenceValue) {
        network.setEvidence(feature.getNodeId(), evidenceValue);
    }

    private static BigDecimal getTrueStateProbability(String nodeId) {
        int outcomePosition = findOutcomePosition(nodeId, TRUE_STATE);
        return BigDecimal.valueOf(network.getNodeValue(nodeId)[outcomePosition]).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    private static int findOutcomePosition(String nodeId, String outcomeId) {
        return Arrays.asList(network.getOutcomeIds(nodeId)).indexOf(outcomeId);
    }

}
