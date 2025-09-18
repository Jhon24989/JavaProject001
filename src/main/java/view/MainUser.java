package view;

import services.Impl.BookServiceImpl;
import services.Impl.LoanServiceImpl;
import services.interfaces.BookServiceInterface;
import services.interfaces.LoanServiceInterface;

import java.util.Scanner;

public class MainUser {
    private final Scanner sc = new Scanner(System.in);
    private final BookServiceInterface libroService = new BookServiceImpl();
    private final LoanServiceInterface prestamoService = new LoanServiceImpl();

    public void mostrar(int usuarioId) {
        boolean salir = false;
        while(!salir) {
            System.out.println("\n--- Panel USUARIO ---");
            System.out.println("1. Listar libros disponibles");
            System.out.println("2. Buscar por título (JOIN)");
            System.out.println("3. Pedir préstamo");
            System.out.println("4. Devolver libro");
            System.out.println("5. Mis préstamos (JOIN)");
            System.out.println("6. Salir");
            System.out.print("Opción: ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> libroService.listarConPrestamosActivos()
                        .stream().filter(l -> l.contains("SI")) // disponible=SI en CSV-like line
                        .forEach(System.out::println);
                case "2" -> {
                    System.out.print("Título contiene: "); String t = sc.nextLine();
                    libroService.buscarPorTituloConJoin(t).forEach(System.out::println);
                }
                case "3" -> {
                    System.out.print("ISBN: "); String isbn = sc.nextLine();
                    try {
                        prestamoService.crearPrestamo(usuarioId, isbn);
                        System.out.println("Préstamo realizado.");
                    } catch (RuntimeException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
                case "4" -> {
                    System.out.print("ISBN: "); String isbn = sc.nextLine();
                    Integer pid = prestamoService.buscarPrestamoActivo(usuarioId, isbn);
                    if (pid == null) System.out.println("No tienes préstamo activo de ese libro.");
                    else { prestamoService.devolverPrestamo(pid); System.out.println("Devolución registrada."); }
                }
                case "5" -> prestamoService.listarPorUsuarioJoin(usuarioId).forEach(System.out::println);
                case "6" -> salir = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }
}
