import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Veuillez saisir le premier nombre :");
        String p = sc.nextLine();

        System.out.println("Veuillez saisir le second nombre :");
        String k = sc.nextLine();

        int sommme = Integer.parseInt(p) + Integer.parseInt(k);

        System.out.printf("La somme de %s et %s est de: %d", p, k, sommme);

    }
}


