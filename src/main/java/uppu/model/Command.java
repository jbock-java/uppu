package uppu.model;

public sealed interface Command permits MoveCommand, WaitCommand {
}
