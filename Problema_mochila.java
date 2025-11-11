import java.util.ArrayList;
import java.util.List;

public class Problema_mochila {

    static class Objeto {
        int peso;
        int valor;
        String nombre;

        public Objeto(String nombre, int peso, int valor) {
            this.nombre = nombre;
            this.peso = peso;
            this.valor = valor;
        }
    }

    public static int mochila(List<Objeto> objetos, int capacidad) {
        int n = objetos.size();
        int[][] M = new int[n][capacidad + 1]; 

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= capacidad; j++) {

                if (i == 0) {
                    if (objetos.get(i).peso <= j)
                        M[i][j] = objetos.get(i).valor;
                    else
                        M[i][j] = 0;
                } else {
                    if (objetos.get(i).peso > j) {
                        M[i][j] = M[i - 1][j];
                    } else {
                        int noTomar = M[i - 1][j];
                        int tomar = M[i - 1][j - objetos.get(i).peso] + objetos.get(i).valor;
                        M[i][j] = Math.max(noTomar, tomar);
                    }
                }
            }
        }

        List<Objeto> seleccionados = new ArrayList<>();
        int i = n - 1;
        int j = capacidad;

        while (i >= 0 && j >= 0) {
            if (i == 0) {
                if (M[i][j] != 0)
                    seleccionados.add(objetos.get(i));
                break;
            }

            if (M[i][j] != M[i - 1][j]) {
                seleccionados.add(objetos.get(i));
                j -= objetos.get(i).peso;
                i--;
            } else {
                i--;
            }
        }

        System.out.println("\nObjetos seleccionados:");
        for (int k = seleccionados.size() - 1; k >= 0; k--) {
            Objeto o = seleccionados.get(k);
            System.out.println("- " + o.nombre + " (peso: " + o.peso + ", valor: " + o.valor + ")");
        }

        return M[n - 1][capacidad];
    }

    // Método principal 
    public static void main(String[] args) {
        List<Objeto> objetos = new ArrayList<>();

        objetos.add(new Objeto("O1", 9, 30));
        objetos.add(new Objeto("O2", 5, 4));
        objetos.add(new Objeto("O4", 3, 5));

        int capacidad = 3;

        System.out.println("Capacidad máxima de la mochila: " + capacidad);
        System.out.println("Objetos disponibles:");
        for (Objeto o : objetos) {
            System.out.println("- " + o.nombre + " (peso: " + o.peso + ", valor: " + o.valor + ")");
        }

        int valorMaximo = mochila(objetos, capacidad);

        System.out.println("\nValor máximo obtenido: " + valorMaximo);
    }
}
