import java.util.ArrayList;
import java.util.Iterator;

public class FileStructure {

    private NLNode<FileObject> root;

    // Creates a FileStructure starting from file object fileObjectName
    public FileStructure(String fileObjectName) throws FileObjectException {
        root = new NLNode<FileObject>();
        FileObject start = new FileObject(fileObjectName);
        root.setData(start);
        findChildren(root);
    }

    // Recursively finds all the children in the FileStructure and adds them to a non-linear tree
    private void findChildren(NLNode<FileObject> r) {
        FileObject f = r.getData();
        // If f is a folder, search all its children
        if (f.isDirectory()) {
            Iterator<FileObject> fIterator = f.directoryFiles();
            while (fIterator.hasNext()) {
                NLNode<FileObject> n = new NLNode<FileObject>(fIterator.next(),r);
                r.addChild(n);
                findChildren(n);
            }
        }
        // Nothing needs to be done if f is a file
    }

    // Returns the root node of the FileStructure
    public NLNode<FileObject> getRoot() {
        return this.root;
    }

    // Returns a String Iterator of all files of a specific type within the FileStructure. Searching is done within findFilesOfType()
    public Iterator<String> filesOfType (String type) {
        return findFilesOfType(root, type, new ArrayList<String>()).iterator();
    }

    // Recursively searches the FileStructure for files of a specific type and collects them in an ArrayList
    private ArrayList<String> findFilesOfType(NLNode<FileObject> r, String type, ArrayList<String> found) {
        FileObject f = r.getData();
        // If f is a file, check its type. If it matches, add it to found
        if (f.isFile()) {
            String fLong = f.getLongName();
            if (fLong.endsWith(type)) {
                found.add(fLong);
            }
        // Otherwise, search all of f's children
        } else {
            Iterator<NLNode<FileObject>> rIterator = r.getChildren();
            rIterator.forEachRemaining(n -> findFilesOfType(n, type, found));
        }
        return found;
    }

    // Returns the full path of a file with a specified name. Searching is done within findFileByName()
    public String findFile(String name) {
        return findFileByName(root, name, "");
    }

    // Recursively searches the FileStructure for file with specific name, returns default value in method call if not found
    // For this program, default found value is empty string ("")
    private String findFileByName(NLNode<FileObject> r, String name, String found) {
        // If a valid file is found, skip the rest of the search
        if (found.equals("")) {
            FileObject f = r.getData();
            // If f is a file, check its name. Update found if it matches
            if (f.isFile()) {
                if (name.equals(f.getName())) {
                    found = f.getLongName();
                }
            // Otherwise, check search all of f's children
            } else {
                Iterator<NLNode<FileObject>> rIterator = r.getChildren();
                while (rIterator.hasNext()) {
                    found = findFileByName(rIterator.next(),name,found);
                }
            }
        }
        return found;
    }

}
