/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webscraper;

/**
 *
 * @author michaelhallam
 */
import java.net.*;
import java.util.ArrayList;
import java.io.*;
public class WebScraper
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        final WebScraper gm = new WebScraper(); // gm = getMethod
        //final String testURL = "https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html"; // test url: links to java doc on urlconnection objects
        String url = "www.bbc.co.uk/news"; // stores the user's URL (default to bbc news)
        //final ArrayList HTML = gm.fetchHTML(testURL);
        ArrayList HTML = null;
        String fileName = "hello"; // stores filename to write to default fileName to write to is hello in current directory
        //final String currentPathway = System.getProperty("user.dir");
        //final String fullPathway = currentPathway + "/hello.txt";
        
        System.out.println("Hello there");
        System.out.println("Please input the url you would like to scrape HTML from");
        
        url = gm.input(); // TODO - check to ensure valid URL
        
        System.out.println("Thanks!");
        System.out.println("Please enter the name of the file you would like it to be stored in");
        
        fileName = gm.input(); // TODO - check to ensure valid file name
        
        System.out.println("Thanks!");
        
        HTML = gm.fetchHTML(url);
        gm.writeToFileProcess(HTML, fileName);
        
        System.out.println("The requested webpage has been written to a file"
                + " in the current directory name " + fileName + ".html");
        System.out.println("Thank you for using this program :)");
        
        /*
        for(int i = 0; i < HTML.size(); i++) // for testing purposes
            System.out.println(HTML.get(i)); // outputs HTML from webpage
        
        System.out.println(fileName);
        gm.writeToFileProcess(HTML, fileName);
        */
    }
    
    private ArrayList<String> fetchHTML(String URLtoScrape) // top level method - works in tandem with scrapeTxt()
    { // return null if something goes wrong
        ArrayList<String> HTML = null;
        URL toScrFrm = null; // to Scrape From
        //URLConnection con = null; // the connection object
        
        try
        {
            toScrFrm = new URL(URLtoScrape); // instantiating a url object
        }
        
        catch(final MalformedURLException ex) // if url formatted incorrectly
        {
            System.out.println("Failed to instantiate URL object");
        }
        
        if(toScrFrm != null) // if the url object has been instantiated
        {
            try
            {
                //con = toScrFrm.openConnection(); // used for testing
                HTML = scrapeTxt(toScrFrm.openConnection()); // scraping txt from given con object
            }
            
            catch(final IOException ex) // if any errors in reading the webpage arise, they will be caught in this try catch block
            {
                System.out.println("Connection failed"); // relevant error message will be 
            }
        }
        
        return HTML; // returns HTML scraped from webpage
    }
    
    private ArrayList<String> scrapeTxt(URLConnection con) // does the hardwork of scraping the text from the webpage
    { // works in tandem with fetchHTML()
        String inputLine = ""; // used to store the next line of HTML
        InputStreamReader ISR = null; 
        BufferedReader BR = null; // used to read the HTML from the webpage
        ArrayList<String> HTML = new ArrayList(); // used to store the HTML
        
        try
        { // instantiating new BufferedReader and InputStreamReader objects
            ISR = new InputStreamReader(con.getInputStream());
            BR = new BufferedReader(ISR);
        }
        
        catch(final IOException ex)
        {
            System.out.println("ERROR INSTANTIATING BR FROM GIVEN URL");
        }
        
        if(BR != null) // if bufferedreader object has been instantiated
        {
            try
            {
                while((inputLine = BR.readLine()) != null) // whilst the end of the webpage has not been reached
                    HTML.add(inputLine); // adds the next command to the arraylist
                BR.close(); // closes the bufferedreader object as it is no longer needed
            }
            
            catch(final IOException ex) // if error in HTML code, IOException is thrown and relevant error message printed
            {
                System.out.println("ERROR READING FROM GIVEN URL");
            }
        }
        return HTML; // returns HTML code
    }
    
    private String input() // when algorithm complete, user should be able to input website & filename of choice
    {
        final InputStreamReader ISR = new InputStreamReader(System.in);
        final BufferedReader BR = new BufferedReader(ISR);
        String toReturn = null;
        
        try
        {
            toReturn = BR.readLine();
        }
        
        catch(final IOException ex)
        {
            toReturn = null;
        }
        
        return toReturn;
    }
    
    private void writeToFileProcess(ArrayList<String> HTML, String fileName) // writes to external text file specified by the file object
    {
        final String currentDirectory = System.getProperty("user.dir"); // gets the directory the program is stored in
        final String fullDirectory = currentDirectory + "/" + fileName + ".html"; // the full pathway leading to where the file is stored
        FileWriter FW = null; // filewriter object - allows for HTML to be written to file
        //PrintWriter PW = null; // not needed - can instantiate into the method directly
        File toWriteTo = new File(fullDirectory); // instantiating a file object - shows the full pathway to the requested file location
        
        try
        {
            FW = new FileWriter(toWriteTo, true); // instantiating object that will write to new file
            //PW = new PrintWriter(FW);
        }
        
        catch(final IOException ex) // if there are any exceptions in the file pathway
        {
            System.out.println("Filewriter object failed to instantiate");
        }
        
        if(FW != null) // if filewriter object has been instantiated
            printToFile(new PrintWriter(FW), HTML); // method that prints to file
    }
    
    private void printToFile(PrintWriter PW, ArrayList<String> HTML)
    {
        for(int i = 0; i < HTML.size(); i++) // whilst the end of the HTML has not been reached
            PW.println(HTML.get(i)); // print the current line of HTML to a txt file
        PW.close(); // closes the output stream
    }
    
}
