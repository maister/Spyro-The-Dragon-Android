# Mi Proyecto de Spyro: Práctica de PMDM 

¡Hola! Soy David Granados y este es mi repositorio para la asignatura de Programación Multimedia. En este proyecto he trabajado sobre una base de Spyro para implementar varias funciones personalizadas y "secretos" (Easter Eggs) que no venían en la app original.

##  ¿Qué he programado?
Lo más interesante ha sido meterle contenido interactivo a las listas de mundos y personajes:

* **El secreto de los Mundos (Vídeo):** Me costó un poco pillar el truco del contador de clics, pero ahora, si pulsas tres veces seguidas en cualquier mundo, la app lanza un vídeo de Spyro a pantalla completa. He tenido que tocar el Manifest para que el vídeo se vea siempre en horizontal (landscpae).
* **La magia de Ripto (Canvas):** Para el villano Ripto, quería algo especial. Al dejar pulsado sobre él, el diamante de su cetro empieza a brillar. Esto lo he hecho dibujando directamente en un "Canvas", creando círculos de colores aleatorios que crecen y se desvanecen. Ha sido un reto entender cómo superponer esta vista sobre la imagen original.

##  Notas técnicas
Para los que reviséis el código, estas son las herramientas clave que he usado:
- **RecyclerView:** Para que las listas vayan fluidas.
- **Custom Views:** La clase `MagicView` es la que gestiona toda la animación del diamante de Ripto.
- **Gestión de eventos:** Control de `onLongClick` y contadores de tiempo para los clics rápidos.

## 📝 Conclusión personal
Este proyecto me ha servido para salirme de los componentes estándar de Android y aprender a dibujar mis propias animaciones. Me he pegado bastante con la parte de Git y la gestión de las vistas en el Layout, pero al final he conseguido que todo encaje y que la app sea mucho más dinámica.

---
**Desarrollado por:** David Granados - 2º DAM
