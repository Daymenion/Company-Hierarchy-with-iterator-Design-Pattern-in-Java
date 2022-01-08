package util;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class OrganizationIterator implements Iterator<IEmployee> {
    Stack<Tree<IEmployee>> stack;

    /**
     *
     * @param root ceomuzdur, organization implementatıon nesnemizle yaratıyoruz ve
     *             bu nesneyi yaratırken de onu yolluyoruz. Stackımızın basına da onu yolluyoruz.
     */
    public OrganizationIterator(Tree<IEmployee> root) {
        stack = new Stack<Tree<IEmployee>>();
        stack.push(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }



    @Override
    public IEmployee next() {
        if (!stack.isEmpty()) {
            Tree<IEmployee> curr = stack.pop();
            //stackden pop yaparak o andaki calısanı alıp datasını alıyoruz.
            IEmployee e = curr.getData();
            List<Tree<IEmployee>> children = curr.children();
            //çocukları sırayla ziyaret etmek istediğimiz için
            // onları yığına ters sırada yerleştiriyorum.
            Collections.reverse(children);
            for (Tree<IEmployee> c : children) {
                stack.push(c);
            }
            return e;
        }
        return null;
    }
}