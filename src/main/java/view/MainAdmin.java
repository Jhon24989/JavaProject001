package view;

import model.domain.Book;
import model.domain.Rol;
import model.domain.User;
import services.Impl.BookServiceImpl;
import services.Impl.LoanServiceImpl;
import services.Impl.UserServiceImpl;
import services.interfaces.BookServiceInterface;
import services.interfaces.LoanServiceInterface;
import services.interfaces.UserServiceInterface;
import util.ExportCSV;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainAdmin {
    private final Scanner sc = new Scanner(System.in);
    private final UserServiceInterface usuarioService = new UserServiceImpl();
    private final BookServiceInterface libroService = new BookServiceImpl();
    private final LoanServiceInterface prestamoService = new LoanServiceImpl();

    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("""
                    --- Panel ADMIN ---
                    1. Gestionar Usuarios
                    2. Gestionar Libros
                    3. Ver Préstamos
                    4. Exportar usuarios a CSV
                    5. Exportar préstamos a CSV
                    6. Salir
                    """);
            System.out.print("Opción: ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> gestionarUsuarios();
                case "2" -> gestionarLibros();
                case "3" -> prestamoService.listarTodosJoin().forEach(System.out::println);
                case "4" -> {
                    var usuarios = usuarioService.listarConPrestamosActivos();
                    String path = ExportCSV.export("usuarios.csv", usuarios);
                    System.out.println("Exportado a: " + path);
                }
                case "5" -> {
                    var prestamos = prestamoService.listarTodosJoin();
                    ArrayList<String> lines = new ArrayList<>();
                    lines.add("id,usuario,email,libro,isbn,prestamo,devolucion");
                    prestamos.forEach(p -> lines.add(
                            p.getId() + "," +
                                    (p.getUsuario() != null ? p.getUsuario().getNombre() : "") + "," +
                                    (p.getUsuario() != null ? p.getUsuario().getEmail() : "") + "," +
                                    (p.getLibro() != null ? p.getLibro().getTitulo() : "") + "," +
                                    p.getLibroIsbn() + "," +
                                    p.getFechaPrestamo() + "," +
                                    p.getFechaDevolucion()
                    ));
                    String path = ExportCSV.export("prestamos.csv", lines);
                    System.out.println("Exportado a: " + path);
                }
                case "6" -> salir = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void gestionarUsuarios() {
        boolean back = false;
        while (!back) {
            System.out.println("""
                    --- Usuarios ---
                    1. Listar usuarios
                    2. Crear
                    3. Editar
                    4. Eliminar
                    5. Volver
                    """);
            System.out.print("Opción: ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> usuarioService.listarConPrestamosActivos().forEach(System.out::println);
                case "2" -> {
                    System.out.print("Nombre: "); String n = sc.nextLine();
                    System.out.print("Email: "); String e = sc.nextLine();
                    System.out.print("Password: "); String p = sc.nextLine();
                    Rol rol = e.endsWith("@admin.com") ? Rol.ADMIN : Rol.USUARIO;
                    usuarioService.registrar(new User(0, n, e, p, rol));
                    System.out.println("Usuario creado.");
                }
                case "3" -> {
                    System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
                    var uOpt = usuarioService.buscarPorId(id);
                    if (uOpt.isEmpty()) { System.out.println("No existe"); break; }
                    User u = uOpt.get();
                    System.out.print("Nuevo nombre ("+u.getNombre()+"): "); String n = sc.nextLine(); if(!n.isBlank()) u.setNombre(n);
                    System.out.print("Nuevo email ("+u.getEmail()+"): "); String e = sc.nextLine(); if(!e.isBlank()) u.setEmail(e);
                    System.out.print("Nueva password: "); String p = sc.nextLine(); if(!p.isBlank()) u.setPassword(p);
                    System.out.print("Nuevo rol ("+u.getRol()+"): "); String r = sc.nextLine(); if(!r.isBlank()) u.setRol(Rol.valueOf(r.toUpperCase()));
                    usuarioService.actualizar(u);
                    System.out.println("Usuario actualizado.");
                }
                case "4" -> {
                    System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
                    usuarioService.eliminar(id);
                    System.out.println("Usuario eliminado.");
                }
                case "5" -> back = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void gestionarLibros() {
        boolean back = false;
        while (!back) {
            System.out.println("""
                    --- Libros ---
                    1. Listar libros 
                    2. Agregar
                    3. Editar
                    4. Eliminar
                    5. Volver
                    """);
            System.out.print("Opción: ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> libroService.listarConPrestamosActivos().forEach(System.out::println);
                case "2" -> {
                    System.out.print("ISBN: "); String isbn = sc.nextLine();
                    System.out.print("Título: "); String titulo = sc.nextLine();
                    System.out.print("Autor: "); String autor = sc.nextLine();
                    libroService.crear(new Book(isbn, titulo, autor, true));
                    System.out.println("Libro agregado.");
                }
                case "3" -> {
                    System.out.print("ISBN: "); String isbn = sc.nextLine();
                    var libro = libroService.buscarPorIsbn(isbn).orElse(null);
                    if (libro == null) { System.out.println("No existe"); break; }
                    System.out.print("Nuevo título ("+libro.getTitulo()+"): "); String t = sc.nextLine(); if(!t.isBlank()) libro.setTitulo(t);
                    System.out.print("Nuevo autor ("+libro.getAutor()+"): "); String a = sc.nextLine(); if(!a.isBlank()) libro.setAutor(a);
                    System.out.print("Disponible (SI/NO): "); String d = sc.nextLine(); if(!d.isBlank()) libro.setDisponible(d.equalsIgnoreCase("SI"));
                    libroService.actualizar(libro);
                    System.out.println("Libro actualizado.");
                }
                case "4" -> {
                    System.out.print("ISBN: "); String isbn = sc.nextLine();
                    libroService.eliminar(isbn);
                    System.out.println("Libro eliminado.");
                }
                case "5" -> back = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }
}
