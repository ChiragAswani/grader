package grades;

import java.math.BigDecimal;

public class GradableCategory {
    private String categoryName;
    private BigDecimal weight_ungrad;
    private BigDecimal weight_grad;
    private int courseid;

    public GradableCategory(){}
    public GradableCategory(int courseid,String categoryName,BigDecimal weightForUngrad,BigDecimal weightForGrad){
        this.courseid=courseid;
        this.categoryName=categoryName;
        weight_grad=weightForGrad;
        weight_ungrad=weightForUngrad;
    }
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getWeight_ungrad() {
        return weight_ungrad;
    }

    public void setWeight_ungrad(BigDecimal weight_ungrad) {
        this.weight_ungrad = weight_ungrad;
    }

    public BigDecimal getWeight_grad() {
        return weight_grad;
    }

    public void setWeight_grad(BigDecimal weight_grad) {
        this.weight_grad = weight_grad;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }
}
