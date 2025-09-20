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
            System.out.println("""
                    --- Panel USUARIO ---
                    1. Listar libros
                    2. Buscar por título
                    3. Pedir préstamo
                    4. Devolver libro
                    5. Mis préstamos
                    6. Salir
                    """);
            System.out.print("Opción: ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> libroService.listarConPrestamosActivos()
                        .stream().filter(l -> l.contains("SI"))
                        .forEach(System.out::println);
                case "2" -> {
                    System.out.print("Título de libro: "); String t = sc.nextLine();
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
