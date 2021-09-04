package by.vashkevich.calculator.helpers

sealed class InputDataState{
    class InputDataStateSuccess: InputDataState()
    class ErrorCheckBoxReplenishmentState(val message:String): InputDataState()
    class ErrorTextFieldsState(val message:String):InputDataState()
    class ErrorRadioButtonState(val message:String):InputDataState()
    class DefaultState:InputDataState()
}
