package Logger;

public class Tools {

    public static String PrintArray(Object[] data){
        StringBuffer stringBuffer=new StringBuffer();

        for(int i=0;i<data.length;i++){
            if(data[i]==null)continue;
            stringBuffer.append(data[i].toString());
            stringBuffer.append(",");
        }

        return stringBuffer.toString();
    }
}
