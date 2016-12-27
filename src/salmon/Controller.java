package salmon;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    private VBox mainWindow;

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private TreeView<File> fileBrowser;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        CodeArea codeArea = new CodeArea();

        Platform.runLater(() ->
        {
            ((Stage) mainWindow.getScene().getWindow()).setMaximized(true);
            mainSplitPane.getItems().add(codeArea);
            mainSplitPane.setDividerPositions(0.15);
        });
    }

    public void addFolderButtonPressed()
    {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Select Folder to Add");
        File selection = fileChooser.showDialog(mainWindow.getScene().getWindow());
        if (selection != null)
        {
            findFiles(selection, null);
        }
    }

    public void exitButtonPressed()
    {
        Platform.exit();
    }

    /**
     * Finds all contents for an input directory and adds them to the file browser
     * @param dir    Root directory to search from
     * @param parent Recursive parameter
     */
    private void findFiles(File dir, TreeItem<File> parent)
    {
        TreeItem<File> root = new TreeItem<>(dir);
        File[] files = dir.listFiles();

        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory())
                {
                    findFiles(file, root);
                }
                else
                {
                    root.getChildren().add(new TreeItem<>(file));
                }
            }
        }

        if (parent == null)
        {
            fileBrowser.setRoot(root);
        }
        else
        {
            parent.getChildren().add(root);
        }
    }
}