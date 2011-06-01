package org.jboss.errai.ioc.rebind.ioc.codegen.builder;


import com.google.gwt.core.ext.typeinfo.JClassType;
import org.jboss.errai.ioc.rebind.ioc.codegen.*;
import org.jboss.errai.ioc.rebind.ioc.codegen.exception.UndefinedConstructorException;
import org.jboss.errai.ioc.rebind.ioc.codegen.meta.MetaClass;

/**
 * @author Mike Brock <cbrock@redhat.com>
 * @author Christian Sadilek <csadilek@redhat.com>
 */
public class ObjectBuilder extends AbstractStatement {
    StringBuilder buf = new StringBuilder();

    private static final int CONSTRUCT_STATEMENT_COMPLETE = 1;
    private static final int SUBCLASSED = 2;
    private static final int FINISHED = 3;

    private MetaClass type;
    private int buildState;

    private ObjectBuilder(MetaClass type) {
        this.type = type;
    }

    public static ObjectBuilder newInstanceOf(MetaClass type) {
        return new ObjectBuilder(type).newInstance();
    }

    public static ObjectBuilder newInstanceOf(Class type) {
        return newInstanceOf(MetaClassFactory.get(type));
    }

    public static ObjectBuilder newInstanceOf(JClassType type) {
        return newInstanceOf(MetaClassFactory.get(type));
    }

    private ObjectBuilder newInstance() {
        buf.append("new ").append(type.getFullyQualifedName());
        return this;
    }

    public ObjectBuilder withParameters(Object... parameters) {
        return withParameters(GenUtil.generateCallParameters(getContext(), parameters));
    }

    public ObjectBuilder withParameters(Statement... parameters) {
        return withParameters(CallParameters.fromStatements(parameters));
    }

    public ObjectBuilder withParameters(CallParameters parameters) {
        if (!type.isInterface() && type.getBestMatchingConstructor(parameters.getParameterTypes()) == null)
            throw new UndefinedConstructorException(type, parameters.getParameterTypes());

        buf.append(parameters.generate(Context.create()));
        buildState |= CONSTRUCT_STATEMENT_COMPLETE;
        return this;
    }

    public ClassStructureBuilder extend() {
        return new ClassStructureBuilder(type);
    }

    public void integrateClassStructure(ClassStructureBuilder builder) {
        finishConstructIfNecessary();
        buf.append(" {\n").append(builder.toJavaString()).append("\n}\n");
    }

    private void finishConstructIfNecessary() {
        if ((buildState & CONSTRUCT_STATEMENT_COMPLETE) == 0) {
            withParameters(CallParameters.none());
        }
    }

    public MetaClass getType() {
        return type;
    }

    public String generate(Context context) {
        finishConstructIfNecessary();
        return buf.toString();
    }
}
