package proysena;

import java.sql.*;

public class Proysena {

    public static void main(String[] args) {
        // Declaración de variables para el acceso a la base de datos 
        String usuario = "root";
        String password = "1234";
        String url = "jdbc:mysql://localhost:3306/senaev01";

        Connection conexion = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            // Cargar el driver (opcional en JDBC 4+)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Crear conexión
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("✅ Conexión exitosa a la base de datos.");

            // Crear objeto Statement
            st = conexion.createStatement();

            // ---------------- INSERT ----------------
            int filasInsert = st.executeUpdate(
                "INSERT INTO usuarios (id, nomusu, passusu) VALUES (3, 'Carlos', 'C123')"
            );
            if (filasInsert > 0) {
                System.out.println("✅ Registro insertado correctamente.");
            }

            // Mostrar después del INSERT
            mostrarUsuarios(st);

            // ---------------- UPDATE ----------------
            int filasUpdate = st.executeUpdate(
                "UPDATE usuarios SET nomusu = 'Carlos Modificado', passusu = 'CM456' WHERE id = 3"
            );
            if (filasUpdate > 0) {
                System.out.println("✅ Registro actualizado correctamente.");
            }

            // Mostrar después del UPDATE
            mostrarUsuarios(st);

            // ---------------- DELETE ----------------
            int filasDelete = st.executeUpdate(
                "DELETE FROM usuarios WHERE id = 3"
            );
            if (filasDelete > 0) {
                System.out.println("✅ Registro eliminado correctamente.");
            }

            // Mostrar después del DELETE
            mostrarUsuarios(st);

        } catch (ClassNotFoundException e) {
            System.out.println("❌ No se encontró el driver de MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Error SQL: " + e.getMessage());
        } finally {
            // Cerrar conexiones en el finally para asegurar cierre
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                System.out.println("⚠️ Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    // Método auxiliar para mostrar usuarios
    private static void mostrarUsuarios(Statement st) throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM usuarios");
        System.out.println("📌 Lista de usuarios:");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") +
                               " | Usuario: " + rs.getString("nomusu") +
                               " | Password: " + rs.getString("passusu"));
        }
        System.out.println("-----------------------------");
        rs.close();
    }
}
