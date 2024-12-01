/*
PARCIAL #3
Proyecto: Simulador De Cajero Automatico
Estudiantes:
Ranses Serrano 8-997-1011
Mohamed Saied 9-757-1995
Luis Medina 9-758-2236
Ibrahim Saied 9-750-2103
*/
//importamos las librerias necesarias
import javax.swing.*;
import java.awt.*;

public class CajeroAutomaticoGUI { //iniciamos los elementos que utilizaremos en el programa
    private JFrame frame; //iniciamos los elementos de la interfaz grafica
    private JPanel mainPanel;
    private JLabel label;
    private JButton btnDepositar, btnRetirar, btnConsultar, btnSalir;
    private int saldo = 0; //iniciamos la variable que llevara el saldo
    private String idioma = "es"; // la variable que se encargara de llevar el idioma, de base sera español
    public static void main(String[] args) { //iniciamos la clase principal
        SwingUtilities.invokeLater(CajeroAutomaticoGUI::new); //la clase principal sera llamada luego, eso se logra mediante la sentencia invokeLater
    }

    public CajeroAutomaticoGUI() { //iniciamos el metodo principal que sera quien llevara el lineamiento de todos los procesoa a realizar
        // seleccionar idioma al iniciar
        seleccionarIdioma(); //metodo de seleccion de idioma

        // crear la ventana principal
        frame = new JFrame(getTexto("titulo")); //creamos el frame y le asignamos un texto
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //indicamos la manera en la que se puede cerrar
        frame.setSize(400, 300); //indicamos el tamaño del frame

        // crear el panel principal
        mainPanel = new JPanel(); //creamos el panel
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10)); //indicamos el tamaño de los elementos dentro del panel

        // crear etiqueta inicial
        label = new JLabel(getTexto("bienvenida"), SwingConstants.CENTER); //iniciamos la etiqueta que contendra el primer texto
        label.setFont(new Font("Arial", Font.BOLD, 16)); //indicamos la fuente tamaño y caracteristicas de este texto

        // Crear botones
        btnDepositar = new JButton(getTexto("depositar")); //iniciamos el boton que realizara el deposito
        btnRetirar = new JButton(getTexto("retirar")); //iniciamos el boton que realizara el retiro
        btnConsultar = new JButton(getTexto("consultar")); //iniciamos el boton que realizara la consulta
        btnSalir = new JButton(getTexto("salir")); //iniciamos el boton que finalizara el programa

        // Agregar acciones a los botones
        btnDepositar.addActionListener(e -> depositar()); //agregamos la accion del deposito
        btnRetirar.addActionListener(e -> retirar()); //agregamos la accion del retiro
        btnConsultar.addActionListener(e -> consultarSaldo()); //agregamos la accion de la consulta
        btnSalir.addActionListener(e -> System.exit(0)); //agregamos la accion de salir, esta se puede añadir directamente mediante el comando system.exit

        // Agregar componentes al panel principal
        mainPanel.add(label); //insertamos el titulo al panel
        mainPanel.add(btnDepositar); //insertamos el boton de depositar
        mainPanel.add(btnRetirar); //insertamos el boton de retirar
        mainPanel.add(btnConsultar); //insertamos el boton de consultar
        mainPanel.add(btnSalir); //insertamos el boton de salir

        // Agregar el panel principal a la ventana
        frame.add(mainPanel); //agregamos el panel a la ventana
        frame.setVisible(true); //indicamos que la ventana sea visible
    }

    private void seleccionarIdioma() { //iniciamos un metodo para el idioma
        Object[] opciones = {"Español", "English"}; //esta variable contendra los idiomas disponibles
        int seleccion = JOptionPane.showOptionDialog( //esta variable contendra la respuesta del usuario
                null,
                "Seleccione el idioma / Select language:", //texto que se le presentara al usuario
                "Idioma / Language", //titulo de la ventana de seleccion de idioma
                JOptionPane.DEFAULT_OPTION, //opcion que en caso de no seleccionar un idioma, se seleccionara automaticamente el español como idioma por default
                JOptionPane.PLAIN_MESSAGE, //tipo de mensaje de la ventana de seleccion de idioma
                null,
                opciones, //indicamos las opciones para seleccionar
                opciones[0] //esta opcion indica los botones de las opciones
        );

        // Asignar el idioma seleccionado
        idioma = (seleccion == 1) ? "en" : "es"; //se selecciona el idioma que elija el usuario
    }

    private String getTexto(String clave) { //metodo que se utilizara para los distintos textos del programa
        // Textos en ambos idiomas
        String[][] textos = { //iniciamos un array bidimensional que contendra 3 elementos, 1-La Clave, 2-El texto en español 3-El texto en ingles
                {"titulo", "Cajero Automático", "ATM"},
                {"bienvenida", "Bienvenido al Cajero Automático", "Welcome to the ATM"},
                {"depositar", "Depositar", "Deposit"},
                {"retirar", "Retirar", "Withdraw"},
                {"consultar", "Consultar Saldo", "Check Balance"},
                {"salir", "Salir", "Exit"},
                {"ingreseDeposito", "Ingrese la cantidad a depositar:", "Enter the amount to deposit:"},
                {"ingreseRetiro", "Ingrese la cantidad a retirar:", "Enter the amount to withdraw:"},
                {"saldoActual", "Su saldo actual es: ", "Your current balance is: "},
                {"depositoExitoso", "Depósito exitoso. Saldo actual: ", "Deposit successful. Current balance: "},
                {"retiroExitoso", "Retiro exitoso. Saldo actual: ", "Withdrawal successful. Current balance: "},
                {"saldoInsuficiente", "Saldo insuficiente.", "Insufficient balance."},
                {"cantidadInvalida", "Ingrese una cantidad válida.", "Enter a valid amount."},
                {"entradaInvalida", "Entrada no válida. Por favor, ingrese un número.", "Invalid input. Please enter a number."}
        };

        // Buscar el texto según la clave y el idioma seleccionado
        for (String[] texto : textos) { //iniciamos un ciclo para cuando se deba realizar la busqueda
            if (texto[0].equals(clave)) { //en caso de que la clave coincida
                return idioma.equals("en") ? texto[2] : texto[1]; //se regresa el texto en el idioma indicado
            }
        }
        return "Texto no encontrado"; // Si la clave no se encuentra se imprimiria este mensaje
    }

    private void depositar() { //Metodo para el deposito
        String cantidadStr = JOptionPane.showInputDialog(frame, getTexto("ingreseDeposito")); //solicitamos al usuario la cantidad a depositar, para manejarla mas facilmente la cantidad se pedira en forma de texto
        if (cantidadStr != null) { //en caso de que la respuesta del usuario no este vacia
            try { //se intentara
                int cantidad = Integer.parseInt(cantidadStr); //transformar el texto del usuario en un valor entero
                if (cantidad > 0) { //en caso de que dicho valor no sea 0 o menor
                    saldo += cantidad; //se añadira el deposito al saldo
                    JOptionPane.showMessageDialog(frame, getTexto("depositoExitoso") + saldo + " USD."); //se indicara al usuario que el deposito fue exitoso y se le mostrara su saldo
                } else { //en caso de que la cantidad sea 0 o menor
                    JOptionPane.showMessageDialog(frame, getTexto("cantidadInvalida")); //se indicara al usuario que la cantidad no es valida
                }
            } catch (NumberFormatException e) { //en caso de que la introduccion del usuario no este en formato numerico
                JOptionPane.showMessageDialog(frame, getTexto("entradaInvalida")); //se indicara al usuario que la entrada no es valida
            }
        }
    }

    private void retirar() { //metodo para retiro
        String cantidadStr = JOptionPane.showInputDialog(frame, getTexto("ingreseRetiro")); //solicitamos al usuario la cantidad a retirar, para manejarla mas facilmente la cantidad se pedira en forma de texto
        if (cantidadStr != null) { //en caso de que la respuesta del usuario no este vacia
            try { //se intentara
                int cantidad = Integer.parseInt(cantidadStr); //transformar el texto del usuario en un valor entero
                if (cantidad > 0 && cantidad <= saldo) { //si la cantidad a retirar es mayor a 0 y dicha cantidad es menor o igual al saldo disponible
                    saldo -= cantidad; //se le restara al saldo la cantidad a retirar
                    JOptionPane.showMessageDialog(frame, getTexto("retiroExitoso") + saldo + " USD."); //se indicara al usuario que el retiro fue exitoso y se le mostrara su saldo
                } else if (cantidad > saldo) { //por otro lado si la cantidad a retirar es mayor al saldo disponible
                    JOptionPane.showMessageDialog(frame, getTexto("saldoInsuficiente")); //se le indicara al usuario que no tiene suficiente saldo
                } else { //en caso de que la cantidad sea 0 o menor
                    JOptionPane.showMessageDialog(frame, getTexto("cantidadInvalida")); //se indicara al usuario que la cantidad no es valida
                }
            } catch (NumberFormatException e) { //en caso de que la introduccion del usuario no este en formato numerico
                JOptionPane.showMessageDialog(frame, getTexto("entradaInvalida")); //se indicara al usuario que la entrada no es valida
            }
        }
    }

    private void consultarSaldo() { //metodo para consultar el saldo
        JOptionPane.showMessageDialog(frame, getTexto("saldoActual") + saldo + " USD."); //simplemente se imprime un mensaje con el saldo actual
    }
}