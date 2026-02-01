El **problema del árbol de recubrimiento mínimo** (_Minimum Spanning Tree_, MST) corresponde a encontrar, en un **grafo no dirigido y conectado con pesos en sus aristas**, un subconjunto de aristas que:

1. **Conecte todos los vértices** (sin dejar ninguno aislado).
2. Forme un **árbol** (es decir, un grafo sin ciclos).
3. Tenga **costo total mínimo**, donde el costo es la suma de los pesos de todas las aristas escogidas.

Sea  *E* el conjunto de las aristas y V el conjunto de vértices. Cada arista la representamos por el par $(i,j), i< j, (i,j) \in E$. Definimos las variables binarias $x_{ij}$ asociadas a cada arista. Creamos las variables continuas $f_{ij}, f_{ji}$ para representar el flujo en la dirección de la arista y  la dirección opuesta. Sea *r* un vértice escogido arbitrariamente.

$$

\begin{array}{ll}
n = |V| & \\
pe(i,j) = j>i,(i,j) \in E & \\
pf(i,j) = (i,j) \in E & \\
\min \sum\limits_{i,j=0|pe(r,j)}^{n-1} w_{ij} x_{ij} & \\
\sum\limits_{j=0|pf(r,j)}^{n-1} f_{rj} - \sum\limits_{j=0|pf(j,r)}^{n-1} f_{jr}  = n & \\
\sum\limits_{i=0|pf(i,j)}^{n-1} f_{ij} - \sum\limits_{k=0|pf(j,k)}^{n-1} f_{jk}  = 1 & j \in 0..n-1| j \neq r\\
f_{ij} + f_{ji} \le (n-1) x_{ij} & i \in 0..n-1, j \in 0..n-1 | pe(i,j)\\
\sum\limits_{i,j=0|p(r,j)}^{n-1} x_{ij} = n -1 & \\
bin \ x_{ij} & i \in 0..n-1, j \in 0..n-1 | pe(i,j) 
\end{array}

$$