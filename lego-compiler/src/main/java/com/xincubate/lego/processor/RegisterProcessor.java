package com.xincubate.lego.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ImportSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.xincubate.lego.annotation.LegoBean;
import com.xincubate.lego.annotation.LegoItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class RegisterProcessor extends AbstractProcessor {

    private Filer mFiler; //文件相关的辅助类
    private Messager mMessager;
    private Elements mElementUtils;
    private Types mTypeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        mTypeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(LegoItem.class.getCanonicalName());
        supportTypes.add(LegoBean.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "processing...");


        MethodSpec.Builder initMethodBuilder = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC);

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(LegoItem.class);
        for (Element element : elements){
            TypeElement typeElement = (TypeElement) element;
            initMethodBuilder.addStatement("LayoutCenter.getInstance().registerViewType($T.class)",mTypeUtils.getDeclaredType(typeElement));
        }

        elements = roundEnvironment.getElementsAnnotatedWith(LegoBean.class);
        for (Element element : elements){
            TypeElement typeElement = (TypeElement) element;
            LegoBean legoBean = element.getAnnotation(LegoBean.class);

            try {
                legoBean.clazz();
            }catch (MirroredTypeException mte) {
                DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
                initMethodBuilder.addStatement("LayoutCenter.getInstance().registerItemBuilder($T.class, new ItemBuilder<$T>() {\npublic BaseItem build(Context context, $T bean) {\nreturn new $T(context,bean);\n}})",mTypeUtils.getDeclaredType(typeElement),mTypeUtils.getDeclaredType(typeElement),mTypeUtils.getDeclaredType(typeElement),classTypeMirror);
            }

        }

        TypeSpec finderClass = TypeSpec.classBuilder("LegoRegisterUtils")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(initMethodBuilder.build())
                .build();
        try {
            JavaFile.builder("com.xincubate.lego.generate", finderClass)
                    .addManualImport(new ImportSpec.Builder("com.xincubate.lego.layoutcenter.LayoutCenter").build())
                    .addManualImport(new ImportSpec.Builder("android.content.Context").build())
                    .addManualImport(new ImportSpec.Builder("com.xincubate.lego.adapter.bean.ItemBuilder").build())
                    .addManualImport(new ImportSpec.Builder("com.xincubate.lego.adapter.core.BaseItem").build())
                    .build().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMessager.printMessage(Diagnostic.Kind.NOTE, "process finish ...");
        return true;
    }
}
