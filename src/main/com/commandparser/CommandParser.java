package com.commandparser;

public class CommandParser {

    public CommandParser(String command){

        String[] words=command.split(" ");
        String commandtype=words[0];
        //System.out.println(words[0]);
        switch (commandtype){

            case "editcontinent":
                if(words[1].equals("-add")){

                    String continentname=words[2];
                    String continentvalues=words[3];
                    System.out.println("add:"+words[2]+" "+words[3]);

                    //Call for adding continent with (continentname,continentvalue) as parameters


                }else if(words[1].equals("-remove")){


                    String continentname=words[2];
                    System.out.println("remove:"+words[2]);
                    //Call for removing the continent name with (continentname) as parameters
                }
                break;

            default:
                System.out.println("Check the input!!");
                break;








        }





    }

}
