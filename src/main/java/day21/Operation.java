package day21;

public class Operation {
    String idLeft;
    String idRight;
    IOperator operator;


    public Operation(final String idLeft, final String idRight, final IOperator operator) {
        super();
        this.idLeft = idLeft;
        this.idRight = idRight;
        this.operator = operator;
    }


    public Polynomial run(final Polynomial left, final Polynomial right) {
        return operator.run(left, right);
    }


    @Override
    public String toString() {
        return idLeft + " " + operator + " " + idRight;
    }


}
