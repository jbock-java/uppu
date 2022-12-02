package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.model.BiCommand;
import uppu.model.Command;

import java.util.ArrayList;
import java.util.List;

import static uppu.model.BiCommand.command;

class S4Checker {

    private final PermutationView view = PermutationView.create();

    public static void main(String[] args) {
        new S4Checker().run();
    }

    private void run() {
        view.setLocationRelativeTo(null);
        List<Product> products = new ArrayList<>();
        products.addAll(oddSelfInverse());
        products.addAll(oddOther());
        products.addAll(mixed());
        products.addAll(selfProducts());
        products.addAll(selfProducts().stream().map(Product::halfInvert).toList());

        List<BiCommand> commands = new ArrayList<>();
        commands.add(new BiCommand(List.of(BiCommand.showState())));
        commands.add(new BiCommand(List.of(BiCommand.wait(10))));
        for (Product p : products) {
            commands.add(p.commands());
        }
        Animation animation = Animation.create(view, 4, 66);
        view.setOnRightArrow(animation::ff);
        view.setOnLeftArrow(animation::rewind);
        view.setOnSpace(animation::pause);
        animation.startAnimation(commands);
    }

    static List<Product> oddSelfInverse() {
        List<Product> products = new ArrayList<>();
        products.add(product(Permutation.create(0, 1), Permutation.create(0, 2, 1, 3)));
        products.add(product(Permutation.create(0, 1), Permutation.create(0, 3, 1, 2)));
        return products;
    }

    static List<Product> oddOther() {
        List<Product> products = new ArrayList<>();
        products.add(product(Permutation.create(0, 1), Permutation.create(0, 1, 2, 3)));
        products.add(product(Permutation.create(0, 1), Permutation.create(0, 3, 2, 1)));
        products.add(product(Permutation.create(0, 1), Permutation.create(0, 1, 3, 2)));
        products.add(product(Permutation.create(0, 1), Permutation.create(0, 2, 3, 1)));
        products.add(product(Permutation.create(0, 2, 1, 3), Permutation.create(0, 1, 2, 3)));
        products.add(product(Permutation.create(0, 2, 1, 3), Permutation.create(0, 3, 2, 1)));
        products.add(product(Permutation.create(0, 2, 1, 3), Permutation.create(0, 1, 3, 2)));
        products.add(product(Permutation.create(0, 2, 1, 3), Permutation.create(0, 2, 3, 1)));
        return products;
    }

    static List<Product> mixed() {
        List<Product> products = new ArrayList<>();
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 2, 1, 3)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 3, 1, 2)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 1, 2, 3)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 3, 2, 1)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 1, 3, 2)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 2, 3, 1)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 1)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(2, 3)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 2)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(1, 3)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 3)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(1, 2)));
        return products;
    }

    static List<Product> selfProducts() {
        List<Product> products = new ArrayList<>();

        // T
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 1, 3)));
        products.add(product(Permutation.create(0, 3, 1), Permutation.create(0, 2, 1)));

        // R
        products.add(product(Permutation.create(0, 3, 1), Permutation.create(1, 2, 3)));
        products.add(product(Permutation.create(1, 3, 2), Permutation.create(0, 1, 3)));

        // B
        products.add(product(Permutation.create(0, 2, 3), Permutation.create(1, 2, 3)));
        products.add(product(Permutation.create(1, 3, 2), Permutation.create(0, 3, 2)));

        // L
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 3, 2)));
        products.add(product(Permutation.create(0, 2, 3), Permutation.create(0, 2, 1)));

        // /
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(1, 2, 3)));
        products.add(product(Permutation.create(1, 3, 2), Permutation.create(0, 2, 1)));

        // \
        products.add(product(Permutation.create(0, 2, 3), Permutation.create(0, 1, 3)));
        products.add(product(Permutation.create(0, 3, 1), Permutation.create(0, 3, 2)));

        return products;
    }

    static Product product(Permutation a, Permutation b) {
        return new Product(a, b);
    }

    private record Product(Permutation a, Permutation b) {

        Product halfInvert() {
            return new Product(a, b.invert());
        }

        BiCommand commands() {
            List<Command> commands = List.of(
                    BiCommand.showState(),
                    BiCommand.wait(28),
                    command(a),
                    BiCommand.wait(1),
                    command(b),
                    BiCommand.wait(16),
                    command(b.compose(a).invert()),
                    BiCommand.wait(28),
                    command(b),
                    BiCommand.wait(1),
                    command(a),
                    BiCommand.wait(16),
                    command(a.compose(b).invert()));
            return new BiCommand(commands);
        }
    }
}