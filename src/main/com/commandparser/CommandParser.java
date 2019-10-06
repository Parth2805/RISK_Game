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


                    for(int i=2;i<words.length;i=i+3){


                        String continentname=words[i];
                        String continentvalues=words[i+1];
                        System.out.println("add:"+words[i]+" "+words[i+1]);

                        //Call for adding continent with (continentname,continentvalue) as parameters




                    }



                }else
                    if(words[1].equals("-remove")){

                        for(int i=2;i<words.length;i=i+2){

                            String continentname=words[i];
                            System.out.println("remove:"+words[i]);
                            //Call for removing the continent name with (continentname) as parameters

                        }


                    }else{

                        System.out.println("Check input!!");

                    }
                break;

            case "editcountry":
                if(words[1].equals("-add")){

                    for(int i=2;i<words.length;i=i+3){

                        String continentname=words[i];
                        String countryname=words[i+1];
                        System.out.println("Editcountry -add:"+continentname+" "+countryname);
                        //Call for adding the country name with (continentname,countryname) as parameters


                    }



                }else
                    if(words[1].equals("-remove")){

                        for(int i=2;i<words.length;i=i+2){

                            String countryname=words[i];
                            System.out.println("Editcountry -remove:"+countryname);

                            //Call for removing the country name with (countryname) as parameter



                        }


                    }else{

                        System.out.println("Wrong input!!");
                    }
                break;

            case "editneighbor":

                if(words[1].equals("-add")){

                        for(int i=2;i<words.length;i=i+3){

                            String countryname=words[i];
                            String neighborcountryname=words[i+1];
                            System.out.println("Edit neighbor add:"+countryname+" "+neighborcountryname);
                            //Call for adding neighbor(countryname,neighborname)
                        }

                }else
                    if(words[1].equals("-remove")){

                        for(int i=2;i<words.length;i=i+3){

                            String countryname=words[i];
                            String neighborcountryname=words[i+1];
                            System.out.println("Edit neighbor remove:"+countryname+" "+neighborcountryname);
                            //Call for removing neighbor(countryname,neighborname)
                        }


                    }
                break;
            default:
                System.out.println("Check the input!!");
                break;








        }





    }

}
