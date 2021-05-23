package group11.restservice.exception;


public class InputInvalidException extends RuntimeException {
    private String fieldname;
    private String input;

    public InputInvalidException(String fieldname, String input) {
        this.fieldname = fieldname;
        this.input = input;
    }

    public String getFieldname() {
        return fieldname;
    }

    public String getInput() {
        return input;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;

    }
    public void setInput(String input) {
        this.input = input;
    }
}