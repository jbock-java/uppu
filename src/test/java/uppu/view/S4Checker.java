package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.model.BiAction;
import uppu.model.BiCommand;
import uppu.model.Slot;
import uppu.products.Product;

import java.util.ArrayList;
import java.util.List;

class S4Checker {

    private final PermutationView view = PermutationView.create();

    public static void main(String[] args) {
        new S4Checker().run();
    }

    private void run() {
        view.setLocationRelativeTo(null);
        List<Product> products = new ArrayList<>();
        products.addAll(oddTimesEven());
        products.addAll(oddTimesSelf());
        products.addAll(oddTimesOdd());
        products.addAll(evenTimesSelf());
        products.addAll(evenTimesEven());

        List<BiCommand> commands = new ArrayList<>();
        commands.add(new BiCommand("", List.of(BiCommand.command(Permutation.identity()))));
        commands.add(new BiCommand("", List.of(BiCommand.wait(10))));
        for (Product p : products) {
            commands.add(p.commands());
        }
        Animation animation = Animation.create(view, 4, (int) (50 * Slot.SCALE));
        view.setOnRightArrow(animation::ff);
        view.setOnLeftArrow(animation::rewind);
        view.setOnSpace(animation::pause);
        List<BiAction> actions = animation.startAnimation(commands);
        view.setActions(actions);
        view.setOnActionSelected(animation::select);
    }

    static List<Product> oddTimesSelf() {
        List<Product> products = new ArrayList<>();
        products.add(product(Permutation.create(0, 1), Permutation.create(0, 2, 1, 3)));
        products.add(product(Permutation.create(0, 1), Permutation.create(0, 3, 1, 2)));
        return products;
    }

    static List<Product> oddTimesOdd() {
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

    static List<Product> oddTimesEven() {
        List<Product> products = new ArrayList<>();
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 1)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 2)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(1, 2)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 2, 3, 1)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 3, 2, 1)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 2, 1, 3)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(2, 3)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(1, 3)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 3)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 3, 1, 2)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 1, 2, 3)));
        products.add(product(Permutation.create(0, 1, 2), Permutation.create(0, 1, 3, 2)));
        return products;
    }

    static List<Product> evenTimesSelf() {
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

    private static List<Product> evenTimesEven() {
        return evenTimesSelf().stream().map(Product::halfInvert).toList();
    }

    static Product product(Permutation a, Permutation b) {
        return new Product(a, b);
    }

}