package FileManager;

import GlobalTools.FunctionClassXMLParaser;

public class LogicFileManager {

    logicStatesOpatrate logicStatesOpatrate=FunctionClassXMLParaser.<logicStatesOpatrate>getClassName("LogicOprate");


    public static LogicFileManager getInstance(){

       return new LogicFileManager();
    }

    public logicStatesOpatrate getLogicStatesOpatrate(){
        return logicStatesOpatrate;
    }
}
