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
        final WebScraper gm = new WebScraper();
        final String testURL = "https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html"; // test url: links to java doc on urlconnection objects
        final ArrayList HTML = gm.fetchHTML(testURL);
        final String fileName = "hello";
        //final String currentPathway = System.getProperty("user.dir");
        //final String fullPathway = currentPathway + "/hello.txt";
        
        for(int i = 0; i < HTML.size(); i++)
            System.out.println(HTML.get(i));
        
        System.out.println(fileName);
        gm.writeToFileProcess(HTML, fileName);
    }
    
    private ArrayList<String> fetchHTML(String URLtoScrape) // top level method
    { // return null if something goes wrong
        ArrayList<String> HTML = null;
        URL toScrFrm = null; // to Scrape From
        //URLConnection con = null; // the connection object
        
        try
        {
            toScrFrm = new URL(URLtoScrape);
        }
        
        catch(final MalformedURLException ex)
        {
            System.out.println("Failed to instantiate URL object");
        }
        
        if(toScrFrm != null)
        {
            try
            {
                //con = toScrFrm.openConnection();
                HTML = scrapeTxt(toScrFrm.openConnection()); // scraping txt from given con object
            }
            
            catch(final IOException ex)
            {
                System.out.println("Connection failed");
            }
        }
        
        return HTML;
    }
    
    private ArrayList<String> scrapeTxt(URLConnection con)
    {
        String inputLine = "";
        InputStreamReader ISR = null;
        BufferedReader BR = null;
        ArrayList<String> HTML = new ArrayList();
        
        try
        {
            ISR = new InputStreamReader(con.getInputStream());
            BR = new BufferedReader(ISR);
        }
        
        catch(final IOException ex)
        {
            System.out.println("ERROR INSTANTIATING BR FROM GIVEN URL");
        }
        
        if(BR != null)
        {
            try
            {
                while((inputLine = BR.readLine()) != null)
                    HTML.add(inputLine);
                BR.close();
            }
            
            catch(final IOException ex)
            {
                System.out.println("ERROR READING FROM GIVEN URL");
            }
        }
        return HTML;
    }
    
    private String input() // when algorithm complete, user should be able to input website of choice
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
    
    // top level method
    
    private void writeToFileProcess(ArrayList<String> HTML, String fileName) // writes to external text file specified by the file object
    { // potentially not working
        final String currentDirectory = System.getProperty("user.dir");
        final String fullDirectory = currentDirectory + "/" + fileName + ".txt"; // TODO - change .txt to .html when finished testing
        FileWriter FW = null;
        PrintWriter PW = null;
        File toWriteTo = new File(fullDirectory);
        
        try
        {
            FW = new FileWriter(toWriteTo, true); // will write to new file
            PW = new PrintWriter(FW);
        }
        
        catch(final IOException ex)
        {
            System.out.println("Filewriter object failed to instantiate");
        }
        
        if(PW != null)
            printToFile(PW, HTML); // will this work?
    }
    
    private void printToFile(PrintWriter PW, ArrayList<String> HTML)
    {
        for(int i = 0; i < HTML.size(); i++)
            PW.println(HTML.get(i));
        PW.close(); // will this work? is it the same instance as the object in the print to txt file function?
    }
    
}
