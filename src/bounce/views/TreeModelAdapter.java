package bounce.views;

import java.util.List;
import java.util.ArrayList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import bounce.NestingShape;
import bounce.ShapeModel;
import bounce.Shape;

public class TreeModelAdapter implements TreeModel {
    private ShapeModel _adaptee;
    private List<TreeModelListener> _listeners = new ArrayList<TreeModelListener>();

    public TreeModelAdapter(ShapeModel model){
        _adaptee = model;
    }

    @Override
    public void addTreeModelListener(TreeModelListener arg0) {
        _listeners.add(arg0);
    }

    @Override
    public Object getChild(Object arg0, int arg1) {
        if (arg0 instanceof NestingShape){
            NestingShape parent = (NestingShape) arg0;
            try {
                return parent.shapeAt(arg1);
            } catch (Exception IndexOutOfBoundsException) {
                return null;
            }
        }
        return null;
    }

    @Override
    public int getChildCount(Object arg0) {
        if (arg0 instanceof NestingShape){
            NestingShape parent = (NestingShape) arg0;
            return parent.shapeCount();
        }
        return 0;
       
    }

    @Override
    public int getIndexOfChild(Object arg0, Object arg1) {
        if (arg0 instanceof NestingShape){
            NestingShape parent = (NestingShape) arg0;
            Shape child = (Shape) arg1;
            if (parent.contains(child)){
                return parent.indexOf(child);
            }
        }
        return -1;
    }

    @Override
    public Object getRoot() {
        return _adaptee.root();
    }

    @Override
    public boolean isLeaf(Object arg0) {
        if (arg0 instanceof NestingShape){
            NestingShape shape = (NestingShape) arg0;
            if (shape.shapeCount() == 0){
                return false;
            }
            return false;
        }
        return true;
    }

    @Override
    public void removeTreeModelListener(TreeModelListener arg0) {
        _listeners.remove(arg0);
    }

    @Override
    public void valueForPathChanged(TreePath arg0, Object arg1) {
        
    }
    
}
