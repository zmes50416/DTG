# DTG
An Open Source library for Document to Graph preprocess.
## Why this project
Many research need transform Texts into a node Graph, so we open source our way to do it. Using Decorator pattern so that you can assemble the process easily. 
## How 
we build every text preprocess as a decorator component and every different text source as a reader.
You can warp decorator on those readers and call the execute method to get the final graph.
reader transfer texts into vertexs(nodes) and different components(TF, POS, NGD, kcore) will filiter or add edges on these vertexs.

## output
we implements plain text and xml format of output. 
