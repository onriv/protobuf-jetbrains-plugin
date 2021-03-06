package io.protostuff.jetbrains.plugin.view.structure;

import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import io.protostuff.jetbrains.plugin.Icons;
import io.protostuff.jetbrains.plugin.psi.EnumNode;
import io.protostuff.jetbrains.plugin.psi.MessageNode;
import io.protostuff.jetbrains.plugin.psi.presentation.ProtoItemPresentation;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

final class MessageTreeElement extends AbstractTreeElement<MessageNode> {

    MessageTreeElement(MessageNode element) {
        super(element);
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        return new ProtoItemPresentation(element.getName(), Icons.MESSAGE);
    }

    @NotNull
    @Override
    public TreeElement[] getChildren() {
        List<TreeElement> treeElements = new ArrayList<>();
        for (PsiElement node : element.getChildren()) {
            // first and the only child
            if (node instanceof MessageNode) {
                TreeElement element = new MessageTreeElement((MessageNode) node);
                treeElements.add(element);
            }
            if (node instanceof EnumNode) {
                TreeElement element = new EnumTreeElement((EnumNode) node);
                treeElements.add(element);
            }
        }
        return treeElements.toArray(new TreeElement[treeElements.size()]);
    }


}
