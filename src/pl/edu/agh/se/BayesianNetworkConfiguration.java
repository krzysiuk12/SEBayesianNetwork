package pl.edu.agh.se;

/**
 * Created by kkicinger on 2015-12-12.
 */
public class BayesianNetworkConfiguration {

    private static final String QUESTION_PREFIX = "Czy jesteś osobą ";
    private static final String QUESTION_SUFFIX = "?";

    public enum Global implements INameable {

        HOTHEAD("Choleryk", "Choleryk"),
        SANGUINE("Sangwinik", "Sangwinik"),
        MELANCHOLIC("Melancholik", "Melancholik"),
        PHLEGMATIC("Flegmatyk", "Flegmatyk"),
        EXTRAVERT_CHARACTER("CharakterEkstrawertyczny", "Ekstrawertyzm"),
        INTORVERT_CHARACTER("CharakterIntrowertyczny", "Intorwertyzm"),
        NEUROSIS("Neurotycznosc", "Neurotyczność"),
        EMOTIONAL_BALANCE("RownowagaEmocjonalna", "Równowaga emocjonalna"),
        DOM("DOM", "Dominujący"),
        DOS("DOS", "Dostosowujący"),
        SYS("SYS", "Systematyczny"),
        MAK("MAK", "Maksymalistyczny"),
        MIN("MIN", "Minimalistyczny"),
        ASE("ASE", "Asekuracyjny"),
        HOJ("HOJ", "Hojny"),
        EMP("EMP", "Emocjonalny");

        private final String nodeId;
        private final String name;

        Global(String nodeId, String name) {
            this.nodeId = nodeId;
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getNodeId() {
            return nodeId;
        }
    }

    public enum Features implements INode {

        COMPOSED("Zrownowazony", QUESTION_PREFIX + "zrównoważoną" + QUESTION_SUFFIX),
        APATHETIC("Apatyczny", QUESTION_PREFIX + "apatyczną" + QUESTION_SUFFIX),
        CONTEMPLATIVE("Refleksyjny", QUESTION_PREFIX + "refleksyjną" + QUESTION_SUFFIX),
        PESSIMISTIC("Pesymistyczny", QUESTION_PREFIX + "pesymistyczną" + QUESTION_SUFFIX),
        NONSOCIAL("Nietowarzyski", QUESTION_PREFIX + "nietowarzyską" + QUESTION_SUFFIX),
        CALM("Spokojny", QUESTION_PREFIX + "spokojną" + QUESTION_SUFFIX),
        CAREFUL("Ostrozny", QUESTION_PREFIX + "ostrożną" + QUESTION_SUFFIX),
        SERIOUS("Powazny", QUESTION_PREFIX + "poważną" + QUESTION_SUFFIX),
        RELIABLE("Solidny", QUESTION_PREFIX + "solidną" + QUESTION_SUFFIX),
        GENTLE("Lagodny", QUESTION_PREFIX + "łagodną" + QUESTION_SUFFIX),
        BOSSY("Wladczy", QUESTION_PREFIX + "władczą" + QUESTION_SUFFIX),
        CARELESS("Beztroski", QUESTION_PREFIX + "beztroską" + QUESTION_SUFFIX),
        LIGHTHEARTED("Niefrasobliwy", QUESTION_PREFIX + "niefrasobliwą" + QUESTION_SUFFIX),
        SENSITIVE("Wrazliwy", QUESTION_PREFIX + "wrażliwą" + QUESTION_SUFFIX),
        OPEN("Otwarty", QUESTION_PREFIX + "otwartą" + QUESTION_SUFFIX),
        SOCIAL("Towarzyski", QUESTION_PREFIX + "towarzyską" + QUESTION_SUFFIX),
        ACTIVE("Aktywny", QUESTION_PREFIX + "aktywną" + QUESTION_SUFFIX),
        OPTIMISTIC("Optymistyczny", QUESTION_PREFIX + "optymistyczną" + QUESTION_SUFFIX),
        IMPULSIVE("Impulsywny", QUESTION_PREFIX + "impulsywną" + QUESTION_SUFFIX),
        SHARP_TEMPERED("Wybuchowy", QUESTION_PREFIX + "wybuchową" + QUESTION_SUFFIX),
        AGGRESSIVE("Agresywny", QUESTION_PREFIX + "agresywną" + QUESTION_SUFFIX),
        IRRITABLE("Drazliwy", QUESTION_PREFIX + "drażliwą" + QUESTION_SUFFIX),;

        private final String nodeId;
        private final String question;

        Features(String nodeId, String question) {
            this.nodeId = nodeId;
            this.question = question;
        }

        public String getNodeId() {
            return nodeId;
        }

        public String getQuestion() {
            return question;
        }
    }

}
