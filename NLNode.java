import java.util.Comparator;
import java.util.Iterator;

public class NLNode<T> {

    private NLNode<T> parent;
    private ListNodes<NLNode<T>> children;
    private T data;

    // Creates an empty non-linear node
    public NLNode() {
        parent = null;
        data = null;
        children = new ListNodes<NLNode<T>>();
    }

    // Creates a nom-linear node with parent node p and containing data d
    public NLNode(T d, NLNode<T> p) {
        parent = p;
        data = d;
        children = new ListNodes<NLNode<T>>();
    }

    // Setter and Getter for parent
    public void setParent(NLNode<T> p) {
        this.parent = p;
    }

    public NLNode<T> getParent() {
        return this.parent;
    }

    // Adds node newChild as a child of this node, and makes this its parent
    public void addChild(NLNode<T> newChild) {
        newChild.setParent(this);
        children.add(newChild);
    }

    // Returns a list of this node's children
    public Iterator<NLNode<T>> getChildren() {
        return children.getList();
    }

    // Returns a sorted list of this node's children
    public Iterator<NLNode<T>> getChildren(Comparator<NLNode<T>> sorter) {
        return children.sortedList(sorter);
    }

    // Setter and Getter for data
    public T getData() {
        return this.data;
    }

    public void setData(T d) {
        this.data = d;
    }

}
