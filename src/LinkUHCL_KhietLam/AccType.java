/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkUHCL_KhietLam;

/**
 *
 * @author kitsune
 */
 public enum AccType{
    
    Recruiter(1), Regular(2);
    private final int option;
    
    AccType(int option){
        this.option=option;
    }
    public static void printTypeList(){
        for(AccType type : AccType.values()){
            System.out.println(type.name());
        }
}   
 }