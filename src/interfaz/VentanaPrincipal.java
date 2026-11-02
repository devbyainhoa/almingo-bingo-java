
package interfaz;

// ======================================================
// IMPORTACIONES DE SWING
// ======================================================

// IMPORTAMOS JFrame PARA CREAR LA VENTANA PRINCIPAL
import javax.swing.JFrame;
// IMPORTAMOS JPanel PARA CREAR CONTENEDORES
import javax.swing.JPanel;
// IMPORTAMOS JLabel PARA MOSTRAR TEXTOS
import javax.swing.JLabel;
// IMPORTAMOS JButton PARA CREAR BOTONES
import javax.swing.JButton;
// IMPORTAMOS JTextField PARA PERMITIR AL USUARIO INTRODUCIR TEXTO
import javax.swing.JTextField;
// IMPORTAMOS JOptionPane PARA MOSTRAR VENTANAS EMERGENTES
import javax.swing.JOptionPane;
// IMPORTAMOS BorderFactory PARA CREAR BORDES EN LOS COMPONENTES
import javax.swing.BorderFactory;
//IMPORTAMOS EL TEMPORIZADOR DE SWING
import javax.swing.Timer;

// ======================================================
// IMPORTACIONES DE AWT
// ======================================================

// IMPORTAMOS Font PARA PERSONALIZAR EL TIPO, TAMAÑO Y ESTILO DE LETRA
import java.awt.Font;
// IMPORTAMOS Color PARA PERSONALIZAR LOS COLORES
import java.awt.Color;
// IMPORTAMOS GridLayout PARA ORGANIZAR COMPONENTES EN FILAS Y COLUMNAS
import java.awt.GridLayout;
// IMPORTAMOS BorderLayout PARA DISTRIBUIR LOS PANELES EN DIFERENTES ZONAS
import java.awt.BorderLayout;
//IMPORTAMOS FLOWLAYOUT PARA DISTRIBUIR LOS COMPONENTES EN UNA FILA
import java.awt.FlowLayout;
//IMPORTAMOS DIMENSION PARA ESTABLECER EL TAMAÑO DE LOS COMPONENTES
import java.awt.Dimension;
// ======================================================
// IMPORTACIONES DE EVENTOS
// ======================================================

// IMPORTAMOS ActionListener PARA GESTIONAR LOS EVENTOS
import java.awt.event.ActionListener;
// IMPORTAMOS ActionEvent PARA REPRESENTAR LOS EVENTOS GENERADOS
import java.awt.event.ActionEvent;

// ======================================================
// IMPORTACIONES DEL PROYECTO
// ======================================================

// IMPORTAMOS LA CLASE CARTON DEL PAQUETE LOGICA
import logica.Carton;
//IMPORTAMOS LA CLASE SORTEO DEL PAQUETE LOGICA
import logica.Sorteo;


public class VentanaPrincipal {
	// GUARDAMOS EL NUMERO ACTUAL DEL SORTEO
	private static int numeroActual = -1;

    public static void main(String[] args) {
    	
        // ======================================================
        // 1. CREACION DE LA VENTANA Y LOS PANELES
        // ======================================================

        // CREAMOS LA VENTANA PRINCIPAL
        JFrame ventana = new JFrame();
        // CREAMOS EL PANEL PRINCIPAL
        JPanel panelPrincipal = new JPanel();
        // CREAMOS UN PANEL PARA MOSTRAR EL TITULO
        JPanel panelTitulo = new JPanel();
        // CREAMOS UN PANEL PARA LOS DATOS DEL JUGADOR
        JPanel panelJugador = new JPanel();
        // CREAMOS UN PANEL PARA MOSTRAR EL CARTON
        JPanel panelCarton = new JPanel();
        // CREAMOS UN PANEL PARA AGRUPAR LOS DATOS DEL JUGADOR Y EL CARTON
        JPanel panelContenido = new JPanel();
        // CREAMOS UN PANEL PARA EL BOTON DE JUGAR
        JPanel panelBoton = new JPanel();
        // CREAMOS UN PANEL PARA INTRODUCIR Y MARCAR LOS NUMEROS
        JPanel panelMarcado = new JPanel();
        // ETIQUETA PARA MOSTRAR EL NUMERO QUE SE EXTRAIGA EN EL SORTEO
        JLabel etiquetaNumero = new JLabel("Esperando sorteo...");

        // ======================================================
        // 2. CONFIGURACION DE LOS LAYOUTS
        // ======================================================

        // DISTRIBUIMOS LOS COMPONENTES PRINCIPALES EN DIFERENTES ZONAS
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        // ORGANIZAMOS LOS DATOS DEL JUGADOR EN TRES FILAS Y UNA COLUMNA
        panelJugador.setLayout(new GridLayout(3, 1, 5, 5));
        // ORGANIZAMOS LOS DATOS DEL JUGADOR, EL CARTON Y EL MARCADO EN TRES FILAS
        panelContenido.setLayout(new GridLayout(3, 1, 5, 10));
     // ORGANIZAMOS EL CAMPO DE TEXTO Y EL BOTON EN UNA FILA CENTRADA
        panelMarcado.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // ESTABLECEMOS UNA DISTRIBUCION DE TRES FILAS Y CUATRO COLUMNAS
        panelCarton.setLayout(new GridLayout(3, 4));


        // ======================================================
        // 3. CREACION Y GENERACION DEL CARTON
        // ======================================================

        // CREAMOS UN OBJETO CARTON PARA EL JUGADOR
        Carton cartonJugador = new Carton();
        // GENERAMOS LOS NUMEROS ALEATORIOS DEL CARTON
        cartonJugador.llenarCarton();
        // CREAMOS UNA MATRIZ DE ETIQUETAS PARA MOSTRAR LOS NUMEROS
        JLabel[][] casillas = new JLabel[3][4];
        Sorteo sorteo = new Sorteo();

        // RECORREMOS LAS FILAS Y COLUMNAS DEL CARTON
        for (int fila = 0; fila < 3; fila++) {

            for (int col = 0; col < 4; col++) {

                // OBTENEMOS EL NUMERO Y CREAMOS UNA ETIQUETA
                casillas[fila][col] = new JLabel(
                    String.valueOf(cartonJugador.obtenerNumero(fila, col))
                );
                // ESTABLECEMOS UN BORDE PARA CADA CASILLA
                casillas[fila][col].setBorder(
                    BorderFactory.createLineBorder(Color.GRAY)
                );
                // HACEMOS VISIBLE EL COLOR DE FONDO
                casillas[fila][col].setOpaque(true);
                // ESTABLECEMOS EL FONDO BLANCO
                casillas[fila][col].setBackground(Color.WHITE);
                // AÑADIMOS LA CASILLA AL PANEL DEL CARTON
                panelCarton.add(casillas[fila][col]);
            }
        }


        // ======================================================
        // 4. CREACION DE LOS COMPONENTES DE LA INTERFAZ
        // ======================================================

        // CREAMOS UNA ETIQUETA PARA MOSTRAR EL TITULO
        JLabel titulo = new JLabel("BINGO ALMINGO");
        // CREAMOS UNA ETIQUETA PARA MOSTRAR EL NOMBRE DEL JUGADOR
        JLabel etiquetaJugador = new JLabel();
        // CREAMOS UN BOTON CON EL TEXTO JUGAR
        JButton botonJugar = new JButton("JUGAR");
        // CREAMOS UN BOTON PARA CONFIRMAR EL NOMBRE DEL JUGADOR
        JButton botonEntrar = new JButton("ENTRAR");
        // CREAMOS UN CAMPO DE TEXTO CON 15 COLUMNAS DE ANCHO
        JTextField campoNombre = new JTextField(15);
        // CREAMOS UN CAMPO DE TEXTO PARA INTRODUCIR EL NUMERO
        JTextField campoNumero = new JTextField(5);
        // CREAMOS UN BOTON PARA MARCAR EL NUMERO INTRODUCIDO
        JButton botonMarcar = new JButton("MARCAR");
        // ESTABLECEMOS EL TAMAÑO DEL CAMPO PARA INTRODUCIR NUMEROS
        campoNumero.setColumns(8);
        // ESTABLECEMOS LA ALTURA DEL CAMPO PARA INTRODUCIR NUMEROS
        campoNumero.setPreferredSize(new Dimension(90, 35));

        // ESTABLECEMOS EL TAMAÑO PREFERIDO DEL BOTON MARCAR
        botonMarcar.setPreferredSize(new Dimension(120, 35));

        // ======================================================
        // 5. PERSONALIZACION DE LOS COMPONENTES
        // ======================================================

        // CREAMOS UNA FUENTE PERSONALIZADA PARA EL TITULO
        Font fuenteTitulo = new Font("Arial", Font.BOLD, 28);
        // CREAMOS UN COLOR PERSONALIZADO PARA EL TITULO
        Color colorTitulo = new Color(142, 68, 173);
        // APLICAMOS LA FUENTE AL TITULO
        titulo.setFont(fuenteTitulo);
        // APLICAMOS EL COLOR AL TEXTO DEL TITULO
        titulo.setForeground(colorTitulo);

        // ======================================================
        // 6. CONFIGURACION DE LA VENTANA
        // ======================================================

        // ESTABLECEMOS EL TITULO DE LA VENTANA
        ventana.setTitle("BINGO ALMINGO");
        // ESTABLECEMOS EL ANCHO Y EL ALTO DE LA VENTANA
        ventana.setSize(700, 550);
        // CENTRAMOS LA VENTANA EN LA PANTALLA
        ventana.setLocationRelativeTo(null);
        // INDICAMOS QUE AL CERRAR LA VENTANA FINALICE LA APLICACION
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // AÑADIMOS EL PANEL PRINCIPAL A LA VENTANA
        ventana.add(panelPrincipal);


        // ======================================================
        // 7. PERSONALIZACION DE LOS PANELES
        // ======================================================

        // ESTABLECEMOS EL COLOR DE FONDO DEL PANEL PRINCIPAL
        panelPrincipal.setBackground(new Color(245, 230, 255));

        // ESTABLECEMOS EL MISMO COLOR EN LOS PANELES SECUNDARIOS
        panelTitulo.setBackground(new Color(245, 230, 255));
        panelJugador.setBackground(new Color(245, 230, 255));
        panelContenido.setBackground(new Color(245, 230, 255));
        panelBoton.setBackground(new Color(245, 230, 255));
        // ESTABLECEMOS EL MISMO COLOR DE FONDO EN EL PANEL DE MARCADO
        panelMarcado.setBackground(new Color(245, 230, 255));


        // ======================================================
        // 8. DISTRIBUCION DE LOS COMPONENTES EN LOS PANELES
        // ======================================================

        // AÑADIMOS EL TITULO A SU PANEL
        panelTitulo.add(titulo);
        // AÑADIMOS EL CAMPO DE TEXTO AL PANEL DEL JUGADOR
        panelJugador.add(campoNombre);
        // AÑADIMOS EL BOTON ENTRAR AL PANEL DEL JUGADOR
        panelJugador.add(botonEntrar);
        // AÑADIMOS LA ETIQUETA DEL JUGADOR A SU PANEL
        panelJugador.add(etiquetaJugador);
        // AÑADIMOS EL PANEL DEL JUGADOR AL PANEL DE CONTENIDO
        panelContenido.add(panelJugador);
        // AÑADIMOS EL PANEL DEL CARTON AL PANEL DE CONTENIDO
        panelContenido.add(panelCarton);
        // AÑADIMOS EL BOTON JUGAR A SU PANEL
        panelBoton.add(botonJugar);
        // AÑADIMOS EL TITULO A LA PARTE SUPERIOR
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        // AÑADIMOS EL CONTENIDO EN LA PARTE CENTRAL
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        // AÑADIMOS EL BOTON JUGAR A LA PARTE INFERIOR
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
        // AÑADIMOS EL CAMPO DE TEXTO AL PANEL DE MARCADO
        panelMarcado.add(campoNumero);
        // AÑADIMOS EL BOTON MARCAR AL PANEL DE MARCADO
        panelMarcado.add(botonMarcar);
        // AÑADIMOS EL PANEL DE MARCADO AL PANEL DE CONTENIDO
        panelContenido.add(panelMarcado);
        // AÑADIMOS LA ETIQUETA QUE MUESTRA EL NUMERO EXTRAIDO AL PANEL
        panelMarcado.add(etiquetaNumero);

        // ======================================================
        // 9. GESTION DE EVENTOS DE LOS BOTONES
        // ======================================================

        // ASIGNAMOS UN ACTIONLISTENER AL BOTON JUGAR
        botonJugar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

            	// DESACTIVAMOS EL BOTON PARA EVITAR INICIAR VARIOS SORTEOS
            	botonJugar.setEnabled(false);
                // MOSTRAMOS UN MENSAJE DE BIENVENIDA
                JOptionPane.showMessageDialog(
                    ventana,
                    "¡Bienvenido a BINGO ALMINGO!"
                );
                // EXTRAEMOS EL PRIMER NUMERO INMEDIATAMENTE
                int primerNumero = sorteo.extraerNumero();
                // GUARDAMOS EL NUMERO COMO NUMERO ACTUAL
                numeroActual = primerNumero;
                // MOSTRAMOS EL PRIMER NUMERO EN LA INTERFAZ
                etiquetaNumero.setText("Número extraído: " + primerNumero);
                
             // CONFIGURAMOS EL TEMPORIZADOR DEL SORTEO
             // ESTABLECEMOS UN INTERVALO DE 15 SEGUNDOS (15000 MILISEGUNDOS)
             // CREAMOS EL TEMPORIZADOR DEL SORTEO
                Timer temporizador = new Timer(15000, new ActionListener() {
                 // DEFINIMOS LA ACCION QUE SE EJECUTARA CADA 10 SEGUNDOS
                 @Override
                 public void actionPerformed(ActionEvent e) {

                	// EXTRAEMOS UN NUMERO DEL SORTEO
                	 int numeroExtraido = sorteo.extraerNumero();

                	 // COMPROBAMOS SI SE HAN EXTRAIDO TODOS LOS NUMEROS
                	 if (numeroExtraido == -1) {

                	     // DETENEMOS EL TEMPORIZADOR
                	     ((Timer) e.getSource()).stop();

                	     // MOSTRAMOS QUE EL SORTEO HA FINALIZADO
                	     etiquetaNumero.setText("SORTEO FINALIZADO");

                	     // INFORMAMOS AL USUARIO DE QUE NO QUEDAN NUMEROS
                	     JOptionPane.showMessageDialog(ventana, "¡Han salido todos los números!");

                	 } else {
                	     // ACTUALIZAMOS EL NUMERO ACTUAL DEL SORTEO
                	     numeroActual = numeroExtraido;
                	     // MOSTRAMOS EL NUMERO EXTRAIDO EN LA INTERFAZ
                	     etiquetaNumero.setText("Número extraído: " + numeroExtraido);
                	  // REACTIVAMOS EL BOTON PARA EL NUEVO TURNO
                	     botonMarcar.setEnabled(true);

                	 }
                 }
             });

             // INICIAMOS EL TEMPORIZADOR
             temporizador.start();
            }
        });

        // ASIGNAMOS UN ACTIONLISTENER AL BOTON ENTRAR
        botonEntrar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

            	// RECUPERAMOS EL NOMBRE Y ELIMINAMOS LOS ESPACIOS INNECESARIOS
            	String nombre = campoNombre.getText().trim();

                // COMPROBAMOS SI EL CAMPO ESTA VACIO
                if (nombre.isEmpty()) {

                    // MOSTRAMOS UN AVISO SI NO SE HA INTRODUCIDO NINGUN NOMBRE
                    JOptionPane.showMessageDialog
                    (
                        ventana,
                        "Debes introducir tu nombre."
                    );
                    

                } else {

                    // MOSTRAMOS UN MENSAJE DE BIENVENIDA CON EL NOMBRE
                    JOptionPane.showMessageDialog(
                        ventana,
                        "¡Bienvenido/a " + nombre + "!"
                    );

                    // LIMPIAMOS EL CAMPO DE TEXTO
                    campoNombre.setText("");
                    // MOSTRAMOS EL NOMBRE DEL JUGADOR EN LA ETIQUETA
                    etiquetaJugador.setText("Jugador: " + nombre);
                    // DESACTIVAMOS EL CAMPO DE TEXTO PARA EVITAR CAMBIAR EL NOMBRE
                    campoNombre.setEnabled(false);
                    // DESACTIVAMOS EL BOTON ENTRAR PARA EVITAR OTRO REGISTRO
                    botonEntrar.setEnabled(false);
                }
                
            }
        });
        
     // ASIGNAMOS UN ACTIONLISTENER AL BOTON MARCAR
        botonMarcar.addActionListener(new ActionListener() 
        {

            public void actionPerformed(ActionEvent e) 
            {
                // RECUPERAMOS EL NUMERO INTRODUCIDO POR EL JUGADOR
                String textoNumero = campoNumero.getText().trim();
                // COMPROBAMOS QUE EL CAMPO NO ESTE VACIO
                if (textoNumero.isEmpty()) {
                    JOptionPane.showMessageDialog(ventana, "Introduce un numero.");
                } else {
                    try {
                    	// CONVERTIMOS EL TEXTO INTRODUCIDO EN UN NUMERO ENTERO
                    	int numero = Integer.parseInt(textoNumero);
                    	// COMPROBAMOS SI EL NUMERO COINCIDE CON EL TURNO ACTUAL
                    	if (numero != numeroActual) {
                    	    // AVISAMOS DE QUE NO ES EL NUMERO DEL TURNO ACTUAL
                    	    JOptionPane.showMessageDialog(ventana, "Solo puedes marcar el numero del turno actual.");

                    	} else {
                    	    // COMPROBAMOS SI EL NUMERO ESTA EN EL CARTON
                    	    boolean encontrado = cartonJugador.marcarNum(numero);
                        if (encontrado) {
                            // RECORREMOS EL CARTON PARA LOCALIZAR EL NUMERO
                            for (int fila = 0; fila < 3; fila++) 
                            {
                                for (int col = 0; col < 4; col++) 
                                {

                                    if (cartonJugador.obtenerNumero(fila, col) == numero) 
                                    {
                                        // MOSTRAMOS UNA X EN LA CASILLA CORRESPONDIENTE
                                        casillas[fila][col].setText(numero + " X");
                                    }
                                }
                            }
                            JOptionPane.showMessageDialog(ventana, "¡Numero marcado!");
                         // DESACTIVAMOS EL BOTON PARA EVITAR MARCAR DOS VECES
                            botonMarcar.setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(ventana, "Ese numero no esta en tu carton.");
                            
                        }
                        // LIMPIAMOS EL CAMPO DE TEXTO
                        campoNumero.setText(""); 
                        }
                    } catch (NumberFormatException ex) 
                    {
                        // MOSTRAMOS UN AVISO SI EL TEXTO NO ES UN NUMERO ENTERO
                        JOptionPane.showMessageDialog(ventana, "Debes introducir un numero valido.");
                    }
                }
            }
        });

        // ======================================================
        // 10. VISUALIZACION DE LA VENTANA
        // ======================================================

        // HACEMOS VISIBLE LA VENTANA
        ventana.setVisible(true);

    }
}
