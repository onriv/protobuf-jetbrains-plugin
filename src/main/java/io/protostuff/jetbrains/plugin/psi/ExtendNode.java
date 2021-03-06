package io.protostuff.jetbrains.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import java.util.HashMap;
import java.util.Map;
import org.antlr.jetbrains.adapter.psi.AntlrPsiNode;
import org.jetbrains.annotations.NotNull;

/**
 * Extend node.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class ExtendNode extends AntlrPsiNode implements KeywordsContainer {

    public ExtendNode(@NotNull ASTNode node) {
        super(node);
    }

    public TypeReferenceNode getTarget() {
        return findChildByClass(TypeReferenceNode.class);
    }

    /**
     * Returns extendee name node, if it exists.
     */
    public ASTNode getTargetNode() {
        TypeReferenceNode target = getTarget();
        if (target != null) {
            return target.getNode();
        }
        return null;
    }

    /**
     * Returns namespace for fields of this extension.
     */
    public String getNamespace() {
        PsiElement parent = getParent();
        while (parent != null) {
            if (parent instanceof DataTypeContainer) {
                DataTypeContainer message = (DataTypeContainer) parent;
                return message.getNamespace();
            }
        }
        return ".";
    }

    /**
     * Returns extension fields.
     */
    public Map<String, MessageField> getExtensionFields() {
        Map<String, MessageField> result = new HashMap<>();
        ExtendEntryNode[] entries = findChildrenByClass(ExtendEntryNode.class);
        for (ExtendEntryNode entry : entries) {
            for (PsiElement element : entry.getChildren()) {
                if (element instanceof MessageField) {
                    MessageField field = (MessageField) element;
                    result.put(field.getFieldName(), field);
                }
            }
        }
        return result;
    }

}
