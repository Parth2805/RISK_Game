package com.commandparser;

public class CommandParser {
    /**
     * Parses the String and calls the relative function.
     * @param command User input Command/String to be parse
     */
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

                }else{

                    System.out.println("Check input!!");

                }
                break;

            case "editcountry":
                if(words[1].equals("-add")){

                    String continentname=words[2];
                    String countryname=words[3];
                    System.out.println("Editcountry -add:"+continentname+" "+countryname);

                    //Call for adding the country name with (continentname,countryname) as parameters

                }else if(words[1].equals("-remove")){

                    String countryname=words[2];
                    System.out.println("Editcountry -remove:"+countryname);

                    //Call for removing the country name with (countryname) as parameter

                }else{

                    System.out.println("Wrong input!!");
                }
                break;
            default:
                System.out.println("Check the input!!");
                break;








        }





    }

}
