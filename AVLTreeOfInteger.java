import java.util.NoSuchElementException;

public class AVLTreeOfInteger {

    private static final class Node {

        public Node father;
        public Node left;
        public Node right;
        public Integer element;

        public Node(Integer element) {
            father = null;
            left = null;
            right = null;
            this.element = element;
        }
    }

    // Atributos        
    private int count; //contagem do número de nodos
    private Node root; //referência para o nodo raiz

    public AVLTreeOfInteger() {
        count = 0;
        root = null;
    }

    public void clear() {
        count = 0;
        root = null;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public int size() {
        return count;
    }

    public Integer getRoot() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        }
        return root.element;
    }

    public void add(Integer element) {
        root = add(root,element,null);
        count++;
    }
    public Node add(Node n, Integer e, Node father) {
        if (n == null) { //insere
            Node aux = new Node(e);
            aux.father = father;
            return aux;
        }
        if(n.element.compareTo(e) < 0) {
            n.right = add(n.right,e,n);
        }
        else {
            n.left = add(n.left,e,n);
        }
        return n;
    }

    public Integer getLeft(Integer element) {
        Node aux = searchNodeRef(element, root);
        if(aux == null)
            throw new NoSuchElementException();
        if(aux.left != null)
            return aux.left.element;
        else
            return null;
    }

    public Integer getRight(Integer element) {
        Node aux = searchNodeRef(element, root);
        if(aux == null)
            throw new NoSuchElementException();
        if(aux.right != null)
            return aux.right.element;
        else
            return null;
    }

    public Integer getParent(Integer element) {
        Node aux = searchNodeRef(element, root);
        if(aux == null)
            throw new NoSuchElementException();
        if(aux.father != null)
            return aux.father.element;
        else
            return null;
    }

    public LinkedListOfInteger positionsPre() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsPreAux(root, res);
        return res;
    }

    private void positionsPreAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            res.add(n.element); //Visita o nodo
            positionsPreAux(n.left, res); //Visita a subárvore da esquerda
            positionsPreAux(n.right, res); //Visita a subárvore da direita
        }
    }

    public LinkedListOfInteger positionsPos() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsPosAux(root, res);
        return res;
    }

    private void positionsPosAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsPosAux(n.left, res); //Visita a subárvore da esquerda
            positionsPosAux(n.right, res); //Visita a subárvore da direita
            res.add(n.element); //Visita o nodo
        }
    }

    public LinkedListOfInteger positionsCentral() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsCentralAux(root, res);
        return res;
    }

    private void positionsCentralAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsCentralAux(n.left, res); //Visita a subárvore da esquerda
            res.add(n.element); //Visita o nodo
            positionsCentralAux(n.right, res); //Visita a subárvore da direita
        }
    }

    public LinkedListOfInteger positionsWidth() {
        Queue<Node> fila = new Queue<>();
        Node atual = null;
        LinkedListOfInteger res = new LinkedListOfInteger();
        if (root != null) {
            fila.enqueue(root);
            while (!fila.isEmpty()) {
                atual = fila.dequeue();
                if (atual.left != null) {
                    fila.enqueue(atual.left);
                }
                if (atual.right != null) {
                    fila.enqueue(atual.right);
                }
                res.add(atual.element);
            }
        }
        return res;
    }

    public String strTraversalCentral() {
        return strTraversalCentral(root);
    }

    private String strTraversalCentral(Node n) {
        return null;
    }

    public boolean contains(Integer element) {
        Node aux = searchNodeRef(element, root);
        return (aux != null);
    }

    private Node searchNodeRef(Integer element, Node target) {
        if (element == null || target == null)
            return null;
        
        int r = element.compareTo(target.element); // equivale ao visita raiz
        
        if (r == 0) // se elementos forem iguais
            return target; // equivale ao visita raiz
        else if (r<0)
            return searchNodeRef(element, target.left);
        else
            return searchNodeRef(element, target.right);
    }

    public boolean remove(Integer element) {
        if (element == null || root == null)
            return false;
        
        Node aux = searchNodeRef(element, root); // procura pelo elemento a ser removido
        
        if (aux==null) // se nao encontrou element
           return false;
        
        remove(aux);
        count--;
        return true;
    }

    // Este metodo recebe referencia para o nodo que 
    // possui o elemento a ser removido
    private void remove(Node n) {
        Node pai = n.father; // guarda ref para o pai do nodo que contem o elemento a ser removido
        
        if (n.left==null && n.right==null) { //se for remocao de nodo folha
            if (n == root) // se tinha apenas um elemento na arvore
                root = null;
            else {
                if (pai.left==n) // se for um filho a esquerda
                    pai.left = null;
                else
                    pai.right = null;
            }
        }
        else if (n.left!=null && n.right!=null) { // se for remocao de nodo com dois filhos
            Node refSmall = smallest(n.right); // verifica o menor elemento a direita
            n.element = refSmall.element;
            remove(refSmall);
        }
        else if (n.left==null) { // se for remocao de um nodo so´ com filho a direita 
            if (n == root) {
                root = n.right;
                root.father = null;
            }
            else {
                if (pai.left == n)
                    pai.left = n.right;
                else
                    pai.right = n.right;
                n.right.father = pai;
            }
        }
        else { // se for remocao de um nodo so´ com filho a esquerda
            if (n == root) {
                root = n.left;
                root.father = null;
            }
            else {
                if (pai.left == n) 
                    pai.left = n.left;
                else
                    pai.right = n.left;
                n.left.father = pai;
            }
        }
    }
    
    private Node smallest(Node n) {
        if (n == null) 
            return null;
        if (n.left == null)
            return n;
        else
            return smallest(n.left);
    }

    /**
     * Altera o elemento em um nodo removendo o elemento antigo e inserindo o novo.
     * @param old
     * @param element
     * @return o elemento antigo que foi removido.
     */

    public Integer set(Integer old, Integer element) {
        Node aux = searchNodeRef(old, root);
        if(aux == null) // Se nao encontrar element
            return null;
        remove(old);
        add(element);
        return old;
    }

    /**
     * Retorna true se element esta armazenado em um nodo externo.
     * @param element
     * @return true se element esta em um nodo externo.
     */

    public boolean isExternal(int element) {
        Node aux = searchNodeRef(element, root);
        if(aux == null) // se nao encontrou element
            throw new NoSuchElementException();
        return (aux.left==null && aux.right==null);
    }
    
    /**
     * Retorna true se element esta armazenado em um nodo interno.
     * @param element
     * @return true se element esta em um nodo interno.
     */

    public boolean isInternal(int element) {
        Node aux = searchNodeRef(element, root);
        if(aux == null)
            throw new NoSuchElementException();
        return (aux != root && (aux.left!=null || aux.right!=null));
    }
    
    public AVLTreeOfInteger clone() {
        AVLTreeOfInteger copy = new AVLTreeOfInteger();
        clone(root, copy);
        return copy;
    }
    private void clone(Node n, AVLTreeOfInteger b) {
        if(n != null) {
            b.add(n.element); // visita a raiz
            clone(n.left, b); // percorre subarvore da esquerda
            clone(n.right, b); // percorre subarvore da direita
        }
    }

    private int countNodes(Node n) {
        if (n == null)
            return 0;
        else
            return 1 + countNodes(n.left) + countNodes(n.right);
    }
    /**
     * Retorna a quantidade de nodos folha.
     * @return Quantidade de nodos folha.
     */

    public int countLeafs() {
        return countLeafsAux(root);
    }
    
    private int countLeafsAux(Node n) { // Recursividade
        if (n == null)
            return 0;
        if (n.left == null && n.right == null) { // Se true retorna 1.
            return 1;
        }
        else {
            return countLeafsAux(n.left) + countLeafsAux(n.right); // Soma as ocorrencias de nodos folha esq e dir.
        }

    }

    public Integer getBiggest() {
        return getBiggestAux(root);
    }
    
    private int getBiggestAux(Node n) {
        if(n == null)
            return n.element;
        else if(n.right == null)
            return n.element;
        else 
            return getBiggestAux(n.right);
    }

}
