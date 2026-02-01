**Enunciado:** Dado un grafo no dirigido *G=(V,E)* con un coste $w_{ij}$ asociado
 a cada arco $(i,j) \in E$, y dos vértices especiales: **origen** s 
 y **destino** t, se desea encontrar un **camino simple** desde s hasta t cuyo 
 **coste total sea mínimo**.

Escogemos las variables binarias $x_{ij}$ para indicar si la arista $(i,j)$ 
está en el camino y alas variables enteras $y_j$ para indicar la posición 
del vértice *j* en el camino.

$$
\begin{array}{ll}
    \min \sum\limits_{(i,j) \in E} w_{ij} x_{ij} &  (i,j) \in E, j >i \\ 
    \sum\limits_{(s,j) \in E} x_{sj} = 1 &   \\ 
    \sum\limits_{(i,t) \in E} x_{it} = 1 &   \\ 
    \sum\limits_{(j,i) \in E} x_{ji} - \sum\limits_{(i,j) \in E} x_{ij} = 1 &  i \in 0 .. n-1 | i \neq s, i \neq t \\
    x_{ij} = 1 \rightarrow y_j - y_i = 1 & (i,j) \in E \\
    y_s = 0 & \\
	bin \ x_{ij} & (i,j) \in E \\
	int \ y_{j} & j  \in 0..n-1
\end{array}
$$