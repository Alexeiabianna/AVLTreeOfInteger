
public class App {
    public static void main(String[] args) {
        AVLTreeOfInteger b = new AVLTreeOfInteger();
        b.add(15);
        b.add(23);
        b.add(9);
        b.add(11);
        b.add(2);
        b.add(20);
        b.add(38);

        System.out.println("Num folhas = "+b.countLeafs());
        System.out.println("Elementos Prefixados = \n"+b.positionsPre());
        System.out.println("Maior elemento = "+b.getBiggest());

        /*
        System.out.println(b.positionsCentral());
        b.remove(15);
        System.out.println("---- Depois remocao do 15 \n"+b.positionsCentral());
        b.remove(11);
         System.out.println("---- Depois remocao do 11 \n"+b.positionsCentral());
        b.remove(2);
         System.out.println("---- Depois remocao do 2 \n"+b.positionsCentral()); 
         */
     }
   
}
