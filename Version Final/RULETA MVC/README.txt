Para compilar y ejecutar el proyecto desde eclipse haciendo click en el proyecto con el botón derecho en Run as->Run Configurations->Program arguments se deben introducir una serie de argumentos:

usage: launcher.Main [-h] [-m <arg>]
 -h,--help         Print this message
 -m,--mode <arg>   Game mode, console or GUI (default)

Todos los argumentos son opcionales, si no se introducen argumentos por defecto no hay mensaje de ayuda y se juega en modo GUI.
En resumen, para seleccionar el modo de juego:
-m CONSOLE
-m GUI
Para pedir ayuda:
-h

Además para que el programa funcione correctamente se deben incluir las carpetas de lib, resources y examples. La carpeta examples es importante porque el fichero de los records está configurado para que por defecto sea el fichero records.json de la carpeta examples.

Por último un detalle a recordar para la configuración inicial del juego una vez estando en el menú es que independientemente de la vista con la que se juega el programa tiene algunos nombres de jugadores reservados para las inteligencias artificiales. Estos son: Juanito, Pepito, Menganito, Juan, Pepe, Mengano, Juanote, Pepote y Menganote. Se dan más detalles en la documentación de la IA.