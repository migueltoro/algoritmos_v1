Se trata de elegir proyectos que dependen de herramientas para su ejecución. Cada proyecto puede requerir un conjunto cualquiera de herramientas, que puede ser vacío o contener varias herramientas. Representamos esto como un grafo bipartito entre proyectos *P* y herramientas *H*, y ponemos una arista $(i,j)$ si el proyecto *i* necesita la herramienta *j*.
Sean:

- *bin* $x_i$ indica si el **proyecto** *i* se ejecuta.
- *bin* $y_j$ indica si la **herramienta** *j* se compra.
- $E⊆P×H$: conjunto de **aristas** del grafo bipartito, es decir, pares $(i,j)$ tales que el proyecto *i* **requiere** la herramienta *j*.

Entonces, para garantizar que un proyecto solo pueda ejecutarse si **todas sus herramientas requeridas** están disponibles, basta con:

$$
  \begin{array}{ll}
    \max \sum\limits_{i \in P} b_i x_i - \sum\limits_{j \in H} c_j y_j & \\
    x_i \le y_j & (i,j) \in E \\
    bin \  x_i & i \in P \\
    bin \  y_j & j \in H \\
  \end{array}{}
$$