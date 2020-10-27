package bounce.views;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import bounce.NestingShape;
import bounce.Shape;
import bounce.ShapeModel;
import bounce.ShapeModelEvent;
import bounce.ShapeModelListener;

public class TreeModelAdapterWithShapeModelListener extends TreeModelAdapter implements ShapeModelListener {

    public TreeModelAdapterWithShapeModelListener(ShapeModel model) {
        super(model);
    }

    @Override
    public void update(ShapeModelEvent event) {
		ShapeModelEvent.EventType eventType = event.eventType();
		
		if(eventType == ShapeModelEvent.EventType.ShapeAdded) {
            NestingShape parent = event.parent();

            Object[] path = parent.path().toArray();
            int[] childIndices = {event.index()};
            Shape[] children = {event.operand()};
            
            TreeModelEvent treeModelEvent = new TreeModelEvent(this, path, childIndices, children);

            for (TreeModelListener listener: _listeners){
                listener.treeNodesInserted(treeModelEvent);
            }
        } 

        else if(eventType == ShapeModelEvent.EventType.ShapeRemoved) {            
            NestingShape parent = event.parent();    
        
            Object[] path = parent.path().toArray();
            int[] childIndices = {event.index()};
            Shape[] children = {event.operand()};
            
            TreeModelEvent treeModelEvent = new TreeModelEvent(this, path, childIndices, children);

            for (TreeModelListener listener: _listeners){
                listener.treeNodesRemoved(treeModelEvent);
            }
        }
    }

}