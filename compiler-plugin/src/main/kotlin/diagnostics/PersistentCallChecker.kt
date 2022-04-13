package me.him188.kotlin.dynamic.delegation.compiler.diagnostics

import com.intellij.psi.PsiElement
import me.him188.kotlin.dynamic.delegation.compiler.backend.DDFqNames
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.parents
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe

class PersistentCallChecker : CallChecker {
    override fun check(resolvedCall: ResolvedCall<*>, reportOn: PsiElement, context: CallCheckerContext) {
        val containingFunction =
            resolvedCall.call.callElement.parents.asSequence().filterIsInstance<KtFunction>().firstOrNull() ?: return
        if (resolvedCall.resultingDescriptor.fqNameSafe != DDFqNames.PERSISTENT) return

        val bodyExpression = containingFunction.bodyExpression ?: return
        if (bodyExpression is KtBlockExpression) {
            context.trace.report(Errors.UNSUPPORTED_BLOCK_BODY.on(reportOn))
        }
    }
}

