/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aptekaprogram;

/**
 *
 * @author Sebcio
 */
public class AptekaProgram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AddKlient ak = new AddKlient();
        ak.setVisible(true);
        
        PokazKlient pk = new PokazKlient();
        pk.setVisible(true);
        
        AddRecepta ar = new AddRecepta();
        ar.setVisible(true);
        
        PokazRecepta pr = new PokazRecepta();
        pr.setVisible(true);
        
        AddDostawa ad = new AddDostawa();
        ad.setVisible(true);
        
        PokazDostawa pd = new PokazDostawa();
        pd.setVisible(true);
        
        AddMagazynLeki aml = new AddMagazynLeki();
        aml.setVisible(true);
        
        PokazMagazynLeki pml = new PokazMagazynLeki();
        pml.setVisible(true);
        
    }
    
}
