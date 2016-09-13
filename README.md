# resolving-dependencies-of-nodes-on-other-nodes-in-Java
Takes an XML File as input describing dependencies of node and prints their Topological order
A sample XML File is attached to understand the format.
Basically, if A has a <Depends> tag on B that means(as per convention followed) that B is dependent on A i.e. A->B
