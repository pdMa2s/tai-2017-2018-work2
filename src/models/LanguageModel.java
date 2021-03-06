package models;

public class LanguageModel {

    private ProbabilityModel probabilityModel;
    private String language;

    public LanguageModel(ProbabilityModel probabilityModel, String language) {
        this.probabilityModel = probabilityModel;
        this.language = language;
    }

    public double bitEstimate(String text){
        int order =  probabilityModel.getOrder();
        if(order == 0)
            return bitEstimateForOrderZero(text);
        return bitEstimateForOrderGreaterThanZero(text, order);
    }
    public double bitEstimateForOrderZero(String text){
        double sum = 0;
        for (int i = 0; i < text.length(); i++){
            char character = text.charAt(i);
            sum -= log2(probabilityModel.getProbability(character));
        }
        return sum;
    }
    public double bitEstimateForOrderGreaterThanZero(String text, int order){
        double sum = 0;
        for (int i = 0; i < text.length() - (order+1); i++){
            String symbol = text.substring(i,i+order);
            char character = text.charAt(i+order);
            sum -= log2(probabilityModel.getProbability(symbol, character));
        }
        return sum;
    }
    private double log2( double a )
    {
        return a == 0 ? 0 : logb(a,2);
    }
    private double logb( double a, double b )
    {
        return Math.log(a) / Math.log(b);
    }
    
    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "'"+ language + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanguageModel that = (LanguageModel) o;

        if (probabilityModel != null ? !probabilityModel.equals(that.probabilityModel) : that.probabilityModel != null)
            return false;
        return language != null ? language.equals(that.language) : that.language == null;
    }

    @Override
    public int hashCode() {
        int result = probabilityModel != null ? probabilityModel.hashCode() : 0;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }
}
