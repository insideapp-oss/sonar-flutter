/*
 * SonarQube Flutter Plugin
 * Copyright (C) 2020 inside|app
 * contact@insideapp.fr
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package fr.insideapp.sonarqube.dart.lang.antlr.generated;// Generated from Dart2.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Dart2Parser}.
 */
public interface Dart2Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(Dart2Parser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(Dart2Parser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(Dart2Parser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(Dart2Parser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#declaredIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterDeclaredIdentifier(Dart2Parser.DeclaredIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#declaredIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitDeclaredIdentifier(Dart2Parser.DeclaredIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#finalConstVarOrType}.
	 * @param ctx the parse tree
	 */
	void enterFinalConstVarOrType(Dart2Parser.FinalConstVarOrTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#finalConstVarOrType}.
	 * @param ctx the parse tree
	 */
	void exitFinalConstVarOrType(Dart2Parser.FinalConstVarOrTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#varOrType}.
	 * @param ctx the parse tree
	 */
	void enterVarOrType(Dart2Parser.VarOrTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#varOrType}.
	 * @param ctx the parse tree
	 */
	void exitVarOrType(Dart2Parser.VarOrTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#initializedVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInitializedVariableDeclaration(Dart2Parser.InitializedVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#initializedVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInitializedVariableDeclaration(Dart2Parser.InitializedVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#initializedIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterInitializedIdentifier(Dart2Parser.InitializedIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#initializedIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitInitializedIdentifier(Dart2Parser.InitializedIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#initializedIdentifierList}.
	 * @param ctx the parse tree
	 */
	void enterInitializedIdentifierList(Dart2Parser.InitializedIdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#initializedIdentifierList}.
	 * @param ctx the parse tree
	 */
	void exitInitializedIdentifierList(Dart2Parser.InitializedIdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#functionSignature}.
	 * @param ctx the parse tree
	 */
	void enterFunctionSignature(Dart2Parser.FunctionSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#functionSignature}.
	 * @param ctx the parse tree
	 */
	void exitFunctionSignature(Dart2Parser.FunctionSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#formalParameterPart}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameterPart(Dart2Parser.FormalParameterPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#formalParameterPart}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameterPart(Dart2Parser.FormalParameterPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(Dart2Parser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(Dart2Parser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(Dart2Parser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(Dart2Parser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(Dart2Parser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(Dart2Parser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameterList(Dart2Parser.FormalParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameterList(Dart2Parser.FormalParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#normalFormalParameters}.
	 * @param ctx the parse tree
	 */
	void enterNormalFormalParameters(Dart2Parser.NormalFormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#normalFormalParameters}.
	 * @param ctx the parse tree
	 */
	void exitNormalFormalParameters(Dart2Parser.NormalFormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#optionalFormalParameters}.
	 * @param ctx the parse tree
	 */
	void enterOptionalFormalParameters(Dart2Parser.OptionalFormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#optionalFormalParameters}.
	 * @param ctx the parse tree
	 */
	void exitOptionalFormalParameters(Dart2Parser.OptionalFormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#optionalPositionalFormalParameters}.
	 * @param ctx the parse tree
	 */
	void enterOptionalPositionalFormalParameters(Dart2Parser.OptionalPositionalFormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#optionalPositionalFormalParameters}.
	 * @param ctx the parse tree
	 */
	void exitOptionalPositionalFormalParameters(Dart2Parser.OptionalPositionalFormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#namedFormalParameters}.
	 * @param ctx the parse tree
	 */
	void enterNamedFormalParameters(Dart2Parser.NamedFormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#namedFormalParameters}.
	 * @param ctx the parse tree
	 */
	void exitNamedFormalParameters(Dart2Parser.NamedFormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#normalFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterNormalFormalParameter(Dart2Parser.NormalFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#normalFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitNormalFormalParameter(Dart2Parser.NormalFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#functionFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFunctionFormalParameter(Dart2Parser.FunctionFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#functionFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFunctionFormalParameter(Dart2Parser.FunctionFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#simpleFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterSimpleFormalParameter(Dart2Parser.SimpleFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#simpleFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitSimpleFormalParameter(Dart2Parser.SimpleFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#fieldFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFieldFormalParameter(Dart2Parser.FieldFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#fieldFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFieldFormalParameter(Dart2Parser.FieldFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#defaultFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterDefaultFormalParameter(Dart2Parser.DefaultFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#defaultFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitDefaultFormalParameter(Dart2Parser.DefaultFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#defaultNamedParameter}.
	 * @param ctx the parse tree
	 */
	void enterDefaultNamedParameter(Dart2Parser.DefaultNamedParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#defaultNamedParameter}.
	 * @param ctx the parse tree
	 */
	void exitDefaultNamedParameter(Dart2Parser.DefaultNamedParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void enterClassDefinition(Dart2Parser.ClassDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void exitClassDefinition(Dart2Parser.ClassDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#mixins}.
	 * @param ctx the parse tree
	 */
	void enterMixins(Dart2Parser.MixinsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#mixins}.
	 * @param ctx the parse tree
	 */
	void exitMixins(Dart2Parser.MixinsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#classMemberDefinition}.
	 * @param ctx the parse tree
	 */
	void enterClassMemberDefinition(Dart2Parser.ClassMemberDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#classMemberDefinition}.
	 * @param ctx the parse tree
	 */
	void exitClassMemberDefinition(Dart2Parser.ClassMemberDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#methodSignature}.
	 * @param ctx the parse tree
	 */
	void enterMethodSignature(Dart2Parser.MethodSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#methodSignature}.
	 * @param ctx the parse tree
	 */
	void exitMethodSignature(Dart2Parser.MethodSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(Dart2Parser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(Dart2Parser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#staticFinalDeclarationList}.
	 * @param ctx the parse tree
	 */
	void enterStaticFinalDeclarationList(Dart2Parser.StaticFinalDeclarationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#staticFinalDeclarationList}.
	 * @param ctx the parse tree
	 */
	void exitStaticFinalDeclarationList(Dart2Parser.StaticFinalDeclarationListContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#staticFinalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStaticFinalDeclaration(Dart2Parser.StaticFinalDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#staticFinalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStaticFinalDeclaration(Dart2Parser.StaticFinalDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#operatorSignature}.
	 * @param ctx the parse tree
	 */
	void enterOperatorSignature(Dart2Parser.OperatorSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#operatorSignature}.
	 * @param ctx the parse tree
	 */
	void exitOperatorSignature(Dart2Parser.OperatorSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(Dart2Parser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(Dart2Parser.OperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#binaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOperator(Dart2Parser.BinaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#binaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOperator(Dart2Parser.BinaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#getterSignature}.
	 * @param ctx the parse tree
	 */
	void enterGetterSignature(Dart2Parser.GetterSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#getterSignature}.
	 * @param ctx the parse tree
	 */
	void exitGetterSignature(Dart2Parser.GetterSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#setterSignature}.
	 * @param ctx the parse tree
	 */
	void enterSetterSignature(Dart2Parser.SetterSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#setterSignature}.
	 * @param ctx the parse tree
	 */
	void exitSetterSignature(Dart2Parser.SetterSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#constructorSignature}.
	 * @param ctx the parse tree
	 */
	void enterConstructorSignature(Dart2Parser.ConstructorSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#constructorSignature}.
	 * @param ctx the parse tree
	 */
	void exitConstructorSignature(Dart2Parser.ConstructorSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#redirection}.
	 * @param ctx the parse tree
	 */
	void enterRedirection(Dart2Parser.RedirectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#redirection}.
	 * @param ctx the parse tree
	 */
	void exitRedirection(Dart2Parser.RedirectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#initializers}.
	 * @param ctx the parse tree
	 */
	void enterInitializers(Dart2Parser.InitializersContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#initializers}.
	 * @param ctx the parse tree
	 */
	void exitInitializers(Dart2Parser.InitializersContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#initializerListEntry}.
	 * @param ctx the parse tree
	 */
	void enterInitializerListEntry(Dart2Parser.InitializerListEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#initializerListEntry}.
	 * @param ctx the parse tree
	 */
	void exitInitializerListEntry(Dart2Parser.InitializerListEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#fieldInitializer}.
	 * @param ctx the parse tree
	 */
	void enterFieldInitializer(Dart2Parser.FieldInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#fieldInitializer}.
	 * @param ctx the parse tree
	 */
	void exitFieldInitializer(Dart2Parser.FieldInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#factoryConstructorSignature}.
	 * @param ctx the parse tree
	 */
	void enterFactoryConstructorSignature(Dart2Parser.FactoryConstructorSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#factoryConstructorSignature}.
	 * @param ctx the parse tree
	 */
	void exitFactoryConstructorSignature(Dart2Parser.FactoryConstructorSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#redirectingFactoryConstructorSignature}.
	 * @param ctx the parse tree
	 */
	void enterRedirectingFactoryConstructorSignature(Dart2Parser.RedirectingFactoryConstructorSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#redirectingFactoryConstructorSignature}.
	 * @param ctx the parse tree
	 */
	void exitRedirectingFactoryConstructorSignature(Dart2Parser.RedirectingFactoryConstructorSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#constantConstructorSignature}.
	 * @param ctx the parse tree
	 */
	void enterConstantConstructorSignature(Dart2Parser.ConstantConstructorSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#constantConstructorSignature}.
	 * @param ctx the parse tree
	 */
	void exitConstantConstructorSignature(Dart2Parser.ConstantConstructorSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#superclass}.
	 * @param ctx the parse tree
	 */
	void enterSuperclass(Dart2Parser.SuperclassContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#superclass}.
	 * @param ctx the parse tree
	 */
	void exitSuperclass(Dart2Parser.SuperclassContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#interfaces}.
	 * @param ctx the parse tree
	 */
	void enterInterfaces(Dart2Parser.InterfacesContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#interfaces}.
	 * @param ctx the parse tree
	 */
	void exitInterfaces(Dart2Parser.InterfacesContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#mixinApplicationClass}.
	 * @param ctx the parse tree
	 */
	void enterMixinApplicationClass(Dart2Parser.MixinApplicationClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#mixinApplicationClass}.
	 * @param ctx the parse tree
	 */
	void exitMixinApplicationClass(Dart2Parser.MixinApplicationClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#mixinApplication}.
	 * @param ctx the parse tree
	 */
	void enterMixinApplication(Dart2Parser.MixinApplicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#mixinApplication}.
	 * @param ctx the parse tree
	 */
	void exitMixinApplication(Dart2Parser.MixinApplicationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#enumType}.
	 * @param ctx the parse tree
	 */
	void enterEnumType(Dart2Parser.EnumTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#enumType}.
	 * @param ctx the parse tree
	 */
	void exitEnumType(Dart2Parser.EnumTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#enumEntry}.
	 * @param ctx the parse tree
	 */
	void enterEnumEntry(Dart2Parser.EnumEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#enumEntry}.
	 * @param ctx the parse tree
	 */
	void exitEnumEntry(Dart2Parser.EnumEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(Dart2Parser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(Dart2Parser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameters(Dart2Parser.TypeParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameters(Dart2Parser.TypeParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#metadata}.
	 * @param ctx the parse tree
	 */
	void enterMetadata(Dart2Parser.MetadataContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#metadata}.
	 * @param ctx the parse tree
	 */
	void exitMetadata(Dart2Parser.MetadataContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(Dart2Parser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(Dart2Parser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#expressionWithoutCascade}.
	 * @param ctx the parse tree
	 */
	void enterExpressionWithoutCascade(Dart2Parser.ExpressionWithoutCascadeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#expressionWithoutCascade}.
	 * @param ctx the parse tree
	 */
	void exitExpressionWithoutCascade(Dart2Parser.ExpressionWithoutCascadeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(Dart2Parser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(Dart2Parser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(Dart2Parser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(Dart2Parser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(Dart2Parser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(Dart2Parser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#nullLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(Dart2Parser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#nullLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(Dart2Parser.NullLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#numericLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteral(Dart2Parser.NumericLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#numericLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteral(Dart2Parser.NumericLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(Dart2Parser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(Dart2Parser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(Dart2Parser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(Dart2Parser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#stringInterpolation}.
	 * @param ctx the parse tree
	 */
	void enterStringInterpolation(Dart2Parser.StringInterpolationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#stringInterpolation}.
	 * @param ctx the parse tree
	 */
	void exitStringInterpolation(Dart2Parser.StringInterpolationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#symbolLiteral}.
	 * @param ctx the parse tree
	 */
	void enterSymbolLiteral(Dart2Parser.SymbolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#symbolLiteral}.
	 * @param ctx the parse tree
	 */
	void exitSymbolLiteral(Dart2Parser.SymbolLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(Dart2Parser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(Dart2Parser.ListLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#mapLiteral}.
	 * @param ctx the parse tree
	 */
	void enterMapLiteral(Dart2Parser.MapLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#mapLiteral}.
	 * @param ctx the parse tree
	 */
	void exitMapLiteral(Dart2Parser.MapLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#mapLiteralEntry}.
	 * @param ctx the parse tree
	 */
	void enterMapLiteralEntry(Dart2Parser.MapLiteralEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#mapLiteralEntry}.
	 * @param ctx the parse tree
	 */
	void exitMapLiteralEntry(Dart2Parser.MapLiteralEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#throwExpression}.
	 * @param ctx the parse tree
	 */
	void enterThrowExpression(Dart2Parser.ThrowExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#throwExpression}.
	 * @param ctx the parse tree
	 */
	void exitThrowExpression(Dart2Parser.ThrowExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#throwExpressionWithoutCascade}.
	 * @param ctx the parse tree
	 */
	void enterThrowExpressionWithoutCascade(Dart2Parser.ThrowExpressionWithoutCascadeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#throwExpressionWithoutCascade}.
	 * @param ctx the parse tree
	 */
	void exitThrowExpressionWithoutCascade(Dart2Parser.ThrowExpressionWithoutCascadeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#functionExpression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionExpression(Dart2Parser.FunctionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#functionExpression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionExpression(Dart2Parser.FunctionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#thisExpression}.
	 * @param ctx the parse tree
	 */
	void enterThisExpression(Dart2Parser.ThisExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#thisExpression}.
	 * @param ctx the parse tree
	 */
	void exitThisExpression(Dart2Parser.ThisExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#nayaExpression}.
	 * @param ctx the parse tree
	 */
	void enterNayaExpression(Dart2Parser.NayaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#nayaExpression}.
	 * @param ctx the parse tree
	 */
	void exitNayaExpression(Dart2Parser.NayaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#constObjectExpression}.
	 * @param ctx the parse tree
	 */
	void enterConstObjectExpression(Dart2Parser.ConstObjectExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#constObjectExpression}.
	 * @param ctx the parse tree
	 */
	void exitConstObjectExpression(Dart2Parser.ConstObjectExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(Dart2Parser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(Dart2Parser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(Dart2Parser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(Dart2Parser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#namedArgument}.
	 * @param ctx the parse tree
	 */
	void enterNamedArgument(Dart2Parser.NamedArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#namedArgument}.
	 * @param ctx the parse tree
	 */
	void exitNamedArgument(Dart2Parser.NamedArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#cascadeSection}.
	 * @param ctx the parse tree
	 */
	void enterCascadeSection(Dart2Parser.CascadeSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#cascadeSection}.
	 * @param ctx the parse tree
	 */
	void exitCascadeSection(Dart2Parser.CascadeSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#cascadeSelector}.
	 * @param ctx the parse tree
	 */
	void enterCascadeSelector(Dart2Parser.CascadeSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#cascadeSelector}.
	 * @param ctx the parse tree
	 */
	void exitCascadeSelector(Dart2Parser.CascadeSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#argumentPart}.
	 * @param ctx the parse tree
	 */
	void enterArgumentPart(Dart2Parser.ArgumentPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#argumentPart}.
	 * @param ctx the parse tree
	 */
	void exitArgumentPart(Dart2Parser.ArgumentPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(Dart2Parser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(Dart2Parser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#compoundAssignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterCompoundAssignmentOperator(Dart2Parser.CompoundAssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#compoundAssignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitCompoundAssignmentOperator(Dart2Parser.CompoundAssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(Dart2Parser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(Dart2Parser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#ifNullExpression}.
	 * @param ctx the parse tree
	 */
	void enterIfNullExpression(Dart2Parser.IfNullExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#ifNullExpression}.
	 * @param ctx the parse tree
	 */
	void exitIfNullExpression(Dart2Parser.IfNullExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(Dart2Parser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(Dart2Parser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(Dart2Parser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(Dart2Parser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(Dart2Parser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(Dart2Parser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#equalityOperator}.
	 * @param ctx the parse tree
	 */
	void enterEqualityOperator(Dart2Parser.EqualityOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#equalityOperator}.
	 * @param ctx the parse tree
	 */
	void exitEqualityOperator(Dart2Parser.EqualityOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(Dart2Parser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(Dart2Parser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#relationalOperator}.
	 * @param ctx the parse tree
	 */
	void enterRelationalOperator(Dart2Parser.RelationalOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#relationalOperator}.
	 * @param ctx the parse tree
	 */
	void exitRelationalOperator(Dart2Parser.RelationalOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#bitwiseOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseOrExpression(Dart2Parser.BitwiseOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#bitwiseOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseOrExpression(Dart2Parser.BitwiseOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#bitwiseXorExpression}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseXorExpression(Dart2Parser.BitwiseXorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#bitwiseXorExpression}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseXorExpression(Dart2Parser.BitwiseXorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#bitwiseAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseAndExpression(Dart2Parser.BitwiseAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#bitwiseAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseAndExpression(Dart2Parser.BitwiseAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#bitwiseOperator}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseOperator(Dart2Parser.BitwiseOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#bitwiseOperator}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseOperator(Dart2Parser.BitwiseOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpression(Dart2Parser.ShiftExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpression(Dart2Parser.ShiftExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#shiftOperator}.
	 * @param ctx the parse tree
	 */
	void enterShiftOperator(Dart2Parser.ShiftOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#shiftOperator}.
	 * @param ctx the parse tree
	 */
	void exitShiftOperator(Dart2Parser.ShiftOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(Dart2Parser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(Dart2Parser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#additiveOperator}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveOperator(Dart2Parser.AdditiveOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#additiveOperator}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveOperator(Dart2Parser.AdditiveOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(Dart2Parser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(Dart2Parser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#multiplicativeOperator}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeOperator(Dart2Parser.MultiplicativeOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#multiplicativeOperator}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeOperator(Dart2Parser.MultiplicativeOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(Dart2Parser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(Dart2Parser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#prefixOperator}.
	 * @param ctx the parse tree
	 */
	void enterPrefixOperator(Dart2Parser.PrefixOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#prefixOperator}.
	 * @param ctx the parse tree
	 */
	void exitPrefixOperator(Dart2Parser.PrefixOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#minusOperator}.
	 * @param ctx the parse tree
	 */
	void enterMinusOperator(Dart2Parser.MinusOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#minusOperator}.
	 * @param ctx the parse tree
	 */
	void exitMinusOperator(Dart2Parser.MinusOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#negationOperator}.
	 * @param ctx the parse tree
	 */
	void enterNegationOperator(Dart2Parser.NegationOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#negationOperator}.
	 * @param ctx the parse tree
	 */
	void exitNegationOperator(Dart2Parser.NegationOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#tildeOperator}.
	 * @param ctx the parse tree
	 */
	void enterTildeOperator(Dart2Parser.TildeOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#tildeOperator}.
	 * @param ctx the parse tree
	 */
	void exitTildeOperator(Dart2Parser.TildeOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#awaitExpression}.
	 * @param ctx the parse tree
	 */
	void enterAwaitExpression(Dart2Parser.AwaitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#awaitExpression}.
	 * @param ctx the parse tree
	 */
	void exitAwaitExpression(Dart2Parser.AwaitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(Dart2Parser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(Dart2Parser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#postfixOperator}.
	 * @param ctx the parse tree
	 */
	void enterPostfixOperator(Dart2Parser.PostfixOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#postfixOperator}.
	 * @param ctx the parse tree
	 */
	void exitPostfixOperator(Dart2Parser.PostfixOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(Dart2Parser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(Dart2Parser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#incrementOperator}.
	 * @param ctx the parse tree
	 */
	void enterIncrementOperator(Dart2Parser.IncrementOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#incrementOperator}.
	 * @param ctx the parse tree
	 */
	void exitIncrementOperator(Dart2Parser.IncrementOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#assignableExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignableExpression(Dart2Parser.AssignableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#assignableExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignableExpression(Dart2Parser.AssignableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#unconditionalAssignableSelector}.
	 * @param ctx the parse tree
	 */
	void enterUnconditionalAssignableSelector(Dart2Parser.UnconditionalAssignableSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#unconditionalAssignableSelector}.
	 * @param ctx the parse tree
	 */
	void exitUnconditionalAssignableSelector(Dart2Parser.UnconditionalAssignableSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#assignableSelector}.
	 * @param ctx the parse tree
	 */
	void enterAssignableSelector(Dart2Parser.AssignableSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#assignableSelector}.
	 * @param ctx the parse tree
	 */
	void exitAssignableSelector(Dart2Parser.AssignableSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(Dart2Parser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(Dart2Parser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#qualified}.
	 * @param ctx the parse tree
	 */
	void enterQualified(Dart2Parser.QualifiedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#qualified}.
	 * @param ctx the parse tree
	 */
	void exitQualified(Dart2Parser.QualifiedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#typeTest}.
	 * @param ctx the parse tree
	 */
	void enterTypeTest(Dart2Parser.TypeTestContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#typeTest}.
	 * @param ctx the parse tree
	 */
	void exitTypeTest(Dart2Parser.TypeTestContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#isOperator}.
	 * @param ctx the parse tree
	 */
	void enterIsOperator(Dart2Parser.IsOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#isOperator}.
	 * @param ctx the parse tree
	 */
	void exitIsOperator(Dart2Parser.IsOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#typeCast}.
	 * @param ctx the parse tree
	 */
	void enterTypeCast(Dart2Parser.TypeCastContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#typeCast}.
	 * @param ctx the parse tree
	 */
	void exitTypeCast(Dart2Parser.TypeCastContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#asOperator}.
	 * @param ctx the parse tree
	 */
	void enterAsOperator(Dart2Parser.AsOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#asOperator}.
	 * @param ctx the parse tree
	 */
	void exitAsOperator(Dart2Parser.AsOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(Dart2Parser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(Dart2Parser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(Dart2Parser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(Dart2Parser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#nonLabledStatment}.
	 * @param ctx the parse tree
	 */
	void enterNonLabledStatment(Dart2Parser.NonLabledStatmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#nonLabledStatment}.
	 * @param ctx the parse tree
	 */
	void exitNonLabledStatment(Dart2Parser.NonLabledStatmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(Dart2Parser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(Dart2Parser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclaration(Dart2Parser.LocalVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclaration(Dart2Parser.LocalVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#localFunctionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalFunctionDeclaration(Dart2Parser.LocalFunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#localFunctionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalFunctionDeclaration(Dart2Parser.LocalFunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(Dart2Parser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(Dart2Parser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(Dart2Parser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(Dart2Parser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#forLoopParts}.
	 * @param ctx the parse tree
	 */
	void enterForLoopParts(Dart2Parser.ForLoopPartsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#forLoopParts}.
	 * @param ctx the parse tree
	 */
	void exitForLoopParts(Dart2Parser.ForLoopPartsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#forInitializerStatement}.
	 * @param ctx the parse tree
	 */
	void enterForInitializerStatement(Dart2Parser.ForInitializerStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#forInitializerStatement}.
	 * @param ctx the parse tree
	 */
	void exitForInitializerStatement(Dart2Parser.ForInitializerStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(Dart2Parser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(Dart2Parser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#doStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoStatement(Dart2Parser.DoStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#doStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoStatement(Dart2Parser.DoStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void enterSwitchStatement(Dart2Parser.SwitchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void exitSwitchStatement(Dart2Parser.SwitchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#switchCase}.
	 * @param ctx the parse tree
	 */
	void enterSwitchCase(Dart2Parser.SwitchCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#switchCase}.
	 * @param ctx the parse tree
	 */
	void exitSwitchCase(Dart2Parser.SwitchCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#defaultCase}.
	 * @param ctx the parse tree
	 */
	void enterDefaultCase(Dart2Parser.DefaultCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#defaultCase}.
	 * @param ctx the parse tree
	 */
	void exitDefaultCase(Dart2Parser.DefaultCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#rethrowStatment}.
	 * @param ctx the parse tree
	 */
	void enterRethrowStatment(Dart2Parser.RethrowStatmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#rethrowStatment}.
	 * @param ctx the parse tree
	 */
	void exitRethrowStatment(Dart2Parser.RethrowStatmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void enterTryStatement(Dart2Parser.TryStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void exitTryStatement(Dart2Parser.TryStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#onPart}.
	 * @param ctx the parse tree
	 */
	void enterOnPart(Dart2Parser.OnPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#onPart}.
	 * @param ctx the parse tree
	 */
	void exitOnPart(Dart2Parser.OnPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#catchPart}.
	 * @param ctx the parse tree
	 */
	void enterCatchPart(Dart2Parser.CatchPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#catchPart}.
	 * @param ctx the parse tree
	 */
	void exitCatchPart(Dart2Parser.CatchPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#finallyPart}.
	 * @param ctx the parse tree
	 */
	void enterFinallyPart(Dart2Parser.FinallyPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#finallyPart}.
	 * @param ctx the parse tree
	 */
	void exitFinallyPart(Dart2Parser.FinallyPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(Dart2Parser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(Dart2Parser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(Dart2Parser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(Dart2Parser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(Dart2Parser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(Dart2Parser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(Dart2Parser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(Dart2Parser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#yieldStatement}.
	 * @param ctx the parse tree
	 */
	void enterYieldStatement(Dart2Parser.YieldStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#yieldStatement}.
	 * @param ctx the parse tree
	 */
	void exitYieldStatement(Dart2Parser.YieldStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#yieldEachStatement}.
	 * @param ctx the parse tree
	 */
	void enterYieldEachStatement(Dart2Parser.YieldEachStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#yieldEachStatement}.
	 * @param ctx the parse tree
	 */
	void exitYieldEachStatement(Dart2Parser.YieldEachStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#assertStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssertStatement(Dart2Parser.AssertStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#assertStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssertStatement(Dart2Parser.AssertStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#assertion}.
	 * @param ctx the parse tree
	 */
	void enterAssertion(Dart2Parser.AssertionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#assertion}.
	 * @param ctx the parse tree
	 */
	void exitAssertion(Dart2Parser.AssertionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#topLevelDefinition}.
	 * @param ctx the parse tree
	 */
	void enterTopLevelDefinition(Dart2Parser.TopLevelDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#topLevelDefinition}.
	 * @param ctx the parse tree
	 */
	void exitTopLevelDefinition(Dart2Parser.TopLevelDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#getOrSet}.
	 * @param ctx the parse tree
	 */
	void enterGetOrSet(Dart2Parser.GetOrSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#getOrSet}.
	 * @param ctx the parse tree
	 */
	void exitGetOrSet(Dart2Parser.GetOrSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#libraryDefinition}.
	 * @param ctx the parse tree
	 */
	void enterLibraryDefinition(Dart2Parser.LibraryDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#libraryDefinition}.
	 * @param ctx the parse tree
	 */
	void exitLibraryDefinition(Dart2Parser.LibraryDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#scriptTag}.
	 * @param ctx the parse tree
	 */
	void enterScriptTag(Dart2Parser.ScriptTagContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#scriptTag}.
	 * @param ctx the parse tree
	 */
	void exitScriptTag(Dart2Parser.ScriptTagContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#libraryName}.
	 * @param ctx the parse tree
	 */
	void enterLibraryName(Dart2Parser.LibraryNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#libraryName}.
	 * @param ctx the parse tree
	 */
	void exitLibraryName(Dart2Parser.LibraryNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#importOrExport}.
	 * @param ctx the parse tree
	 */
	void enterImportOrExport(Dart2Parser.ImportOrExportContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#importOrExport}.
	 * @param ctx the parse tree
	 */
	void exitImportOrExport(Dart2Parser.ImportOrExportContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#dottedIdentifierList}.
	 * @param ctx the parse tree
	 */
	void enterDottedIdentifierList(Dart2Parser.DottedIdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#dottedIdentifierList}.
	 * @param ctx the parse tree
	 */
	void exitDottedIdentifierList(Dart2Parser.DottedIdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#libraryimport}.
	 * @param ctx the parse tree
	 */
	void enterLibraryimport(Dart2Parser.LibraryimportContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#libraryimport}.
	 * @param ctx the parse tree
	 */
	void exitLibraryimport(Dart2Parser.LibraryimportContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#importSpecification}.
	 * @param ctx the parse tree
	 */
	void enterImportSpecification(Dart2Parser.ImportSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#importSpecification}.
	 * @param ctx the parse tree
	 */
	void exitImportSpecification(Dart2Parser.ImportSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#combinator}.
	 * @param ctx the parse tree
	 */
	void enterCombinator(Dart2Parser.CombinatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#combinator}.
	 * @param ctx the parse tree
	 */
	void exitCombinator(Dart2Parser.CombinatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#identifierList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierList(Dart2Parser.IdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#identifierList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierList(Dart2Parser.IdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#libraryExport}.
	 * @param ctx the parse tree
	 */
	void enterLibraryExport(Dart2Parser.LibraryExportContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#libraryExport}.
	 * @param ctx the parse tree
	 */
	void exitLibraryExport(Dart2Parser.LibraryExportContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#partDirective}.
	 * @param ctx the parse tree
	 */
	void enterPartDirective(Dart2Parser.PartDirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#partDirective}.
	 * @param ctx the parse tree
	 */
	void exitPartDirective(Dart2Parser.PartDirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#partHeader}.
	 * @param ctx the parse tree
	 */
	void enterPartHeader(Dart2Parser.PartHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#partHeader}.
	 * @param ctx the parse tree
	 */
	void exitPartHeader(Dart2Parser.PartHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#partDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPartDeclaration(Dart2Parser.PartDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#partDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPartDeclaration(Dart2Parser.PartDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#uri}.
	 * @param ctx the parse tree
	 */
	void enterUri(Dart2Parser.UriContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#uri}.
	 * @param ctx the parse tree
	 */
	void exitUri(Dart2Parser.UriContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#configurableUri}.
	 * @param ctx the parse tree
	 */
	void enterConfigurableUri(Dart2Parser.ConfigurableUriContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#configurableUri}.
	 * @param ctx the parse tree
	 */
	void exitConfigurableUri(Dart2Parser.ConfigurableUriContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#configurationUri}.
	 * @param ctx the parse tree
	 */
	void enterConfigurationUri(Dart2Parser.ConfigurationUriContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#configurationUri}.
	 * @param ctx the parse tree
	 */
	void exitConfigurationUri(Dart2Parser.ConfigurationUriContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#uriTest}.
	 * @param ctx the parse tree
	 */
	void enterUriTest(Dart2Parser.UriTestContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#uriTest}.
	 * @param ctx the parse tree
	 */
	void exitUriTest(Dart2Parser.UriTestContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#dtype}.
	 * @param ctx the parse tree
	 */
	void enterDtype(Dart2Parser.DtypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#dtype}.
	 * @param ctx the parse tree
	 */
	void exitDtype(Dart2Parser.DtypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(Dart2Parser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(Dart2Parser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void enterTypeArguments(Dart2Parser.TypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void exitTypeArguments(Dart2Parser.TypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#typeList}.
	 * @param ctx the parse tree
	 */
	void enterTypeList(Dart2Parser.TypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#typeList}.
	 * @param ctx the parse tree
	 */
	void exitTypeList(Dart2Parser.TypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#typeAlias}.
	 * @param ctx the parse tree
	 */
	void enterTypeAlias(Dart2Parser.TypeAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#typeAlias}.
	 * @param ctx the parse tree
	 */
	void exitTypeAlias(Dart2Parser.TypeAliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#typeAliasBody}.
	 * @param ctx the parse tree
	 */
	void enterTypeAliasBody(Dart2Parser.TypeAliasBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#typeAliasBody}.
	 * @param ctx the parse tree
	 */
	void exitTypeAliasBody(Dart2Parser.TypeAliasBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#functionTypeAlias}.
	 * @param ctx the parse tree
	 */
	void enterFunctionTypeAlias(Dart2Parser.FunctionTypeAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#functionTypeAlias}.
	 * @param ctx the parse tree
	 */
	void exitFunctionTypeAlias(Dart2Parser.FunctionTypeAliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link Dart2Parser#functionPrefix}.
	 * @param ctx the parse tree
	 */
	void enterFunctionPrefix(Dart2Parser.FunctionPrefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link Dart2Parser#functionPrefix}.
	 * @param ctx the parse tree
	 */
	void exitFunctionPrefix(Dart2Parser.FunctionPrefixContext ctx);
}