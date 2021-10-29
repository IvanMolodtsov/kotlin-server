package com.vanmo.ioc.scope

import com.vanmo.common.interfaces.Usable
import com.vanmo.ioc.dependencies.ExecuteInScope
import com.vanmo.ioc.dependencies.Register
import com.vanmo.ioc.dependencies.ScopeNew
import com.vanmo.ioc.dependencies.Unregister
import com.vanmo.resolve

fun RootScope.init() {
    store["IoC.Register"] = Register()
    store["IoC.Unregister"] = Unregister()
    store["Scopes.New"] = ScopeNew()
    store["Scopes.executeInScope"] = ExecuteInScope()
    store["Scopes.executeInNewScope"] = {
        val scope: IScope = resolve("Scopes.New")
        resolve<Usable>("Scopes.executeInScope", scope)
    }
    store["Scopes.Root"] = { this }
}
