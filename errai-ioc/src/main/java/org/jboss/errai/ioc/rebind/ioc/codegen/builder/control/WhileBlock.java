package org.jboss.errai.ioc.rebind.ioc.codegen.builder.control;

import org.jboss.errai.ioc.rebind.ioc.codegen.Context;
import org.jboss.errai.ioc.rebind.ioc.codegen.Statement;

/**
 * @author Mike Brock <cbrock@redhat.com>
 */
public class WhileBlock extends AbstractBlockConditional {
    public WhileBlock(Statement condition, Statement block) {
        super(condition, block);
    }

    public String generate(Context context) {
        StringBuilder builder = new StringBuilder("while (")
                .append(getCondition().generate(context)).append(") {\n");

        if (getBlock() != null) {
            builder.append(getBlock().generate(context));
        }

        builder.append("}\n");

        return builder.toString();
    }

    public Context getContext() {
        return null;
    }
}
