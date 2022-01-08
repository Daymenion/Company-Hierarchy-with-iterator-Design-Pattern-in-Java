package util;

public abstract class AbstractTree<T> implements Tree<T> {
    protected T data;

    /*
    Ağacın düğümlerinin içereceği bilginin tutulmasını ve
    agacın yaprakları ile yaprak grupları arasındaki bağlantıyı sağlamak için createNode methodunu içerir
     */
    public AbstractTree(T data) {
        this.data = data;
    }


    public T getData() {
        return data;
    }

    protected abstract <R> Tree<R> createNode(R data);
}

