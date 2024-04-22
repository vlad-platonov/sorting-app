package com.company.sortnumbers.view.swing;

import com.company.sortnumbers.util.model.SwapPair;
import java.awt.Point;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.Animator.Builder;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;
import org.springframework.stereotype.Component;

@Component
public class SwingAnimation {

    private MainMenuFrame frame;

    public void startAnimation(MainMenuFrame frame, Set<SwapPair> swapPairs) {
        this.frame = frame;
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        frame.setAnimating(true);

        for (SwapPair pair : swapPairs) {
            JButton button1 = frame.getButtonWithNumber(pair.getFirst());
            JButton button2 = frame.getButtonWithNumber(pair.getSecond());

            future = future.thenCompose(v -> moveButtons(button1, button2))
                .thenRun(() -> Collections.swap(frame.getNumberButtons(),
                    frame.getNumberButtons().indexOf(button1),
                    frame.getNumberButtons().indexOf(button2)));
        }
        future.thenRun(swapPairs::clear).thenRun(() -> frame.setAnimating(false));
    }

    private CompletableFuture<Void> moveButtons(JButton button1, JButton button2) {
        Point originalLocation1 = button1.getLocation();
        Point originalLocation2 = button2.getLocation();

        CompletableFuture<Void> future1 = moveButton(button1, originalLocation2);
        return future1.thenCompose(v -> moveButton(button2, originalLocation1));
    }

    private CompletableFuture<Void> moveButton(JButton button, Point targetLocation) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Point startLocation = button.getLocation();

        Animator animator = new Builder()
            .setDuration(500, TimeUnit.MILLISECONDS)
            .addTarget(new TimingTargetAdapter() {
                @Override
                public void timingEvent(Animator source, double fraction) {
                    double dx = fraction * (targetLocation.x - startLocation.x);
                    double dy = fraction * (targetLocation.y - startLocation.y);
                    button.setLocation((int) (startLocation.x + dx), (int) (startLocation.y + dy));
                    frame.getNumberButtonPanel().repaint();
                }

                @Override
                public void end(Animator source) {
                    future.complete(null);
                }
            })
            .build();

        animator.start();
        return future;
    }
}
