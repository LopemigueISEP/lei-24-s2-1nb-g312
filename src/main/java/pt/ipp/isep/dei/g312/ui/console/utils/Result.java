package pt.ipp.isep.dei.g312.ui.console.utils;

public class Result {
    public String message;
    public boolean hasError;

    public Result(){
        this.message="";
        this.hasError=false;
    }

    public Result(String message, boolean hasError) {
        this.message=message;
        this.hasError=hasError;
    }
}
