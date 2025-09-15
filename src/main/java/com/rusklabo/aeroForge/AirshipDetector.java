package com.rusklabo.aeroForge;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class AirshipDetector implements Listener {

    private final AeroForge plugin;
    private final int MAX_BLOCK_COUNT;

    public AirshipDetector(AeroForge plugin) {
        this.plugin = plugin;
        this.MAX_BLOCK_COUNT = plugin.getConfig().getInt("max-block-count", 500);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // 右クリックかつブロックがクリックされた場合
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        Player player = event.getPlayer();

        // 看板ブロックかチェック
        if (!(clickedBlock.getState() instanceof Sign)) {
            return;
        }

        Sign sign = (Sign) clickedBlock.getState();
        String signText = sign.getLine(0);

        // 看板の1行目が "Airship" かチェック
        if (!signText.equalsIgnoreCase("Airship")) {
            return;
        }

        // 看板の真後ろのブロックを起点として取得
        Block startBlock = null;
        if (clickedBlock.getBlockData() instanceof WallSign) {
            WallSign wallSign = (WallSign) clickedBlock.getBlockData();
            startBlock = clickedBlock.getRelative(wallSign.getFacing().getOppositeFace());
        } else {
            // 看板が地面に設置されている場合など
            startBlock = clickedBlock.getRelative(event.getBlockFace().getOppositeFace());
        }

        if (startBlock == null || startBlock.getType().isAir()) {
            player.sendMessage("§c乗り物の起点がありません。");
            return;
        }

        // 幅優先探索 (BFS) で船体を検出
        Set<Block> shipBlocks = new HashSet<>();
        Queue<Block> queue = new LinkedList<>();

        queue.add(startBlock);
        shipBlocks.add(startBlock);

        while (!queue.isEmpty()) {
            Block currentBlock = queue.poll();

            // 最大ブロック数に達したら探索を中止
            if (shipBlocks.size() >= MAX_BLOCK_COUNT) {
                player.sendMessage("§c乗り物が大きすぎます！ (最大ブロック数: " + MAX_BLOCK_COUNT + ")");
                return;
            }

            // 周囲6方向のブロックを探索
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue;
                        if (Math.abs(dx) + Math.abs(dy) + Math.abs(dz) > 1) continue; // 隣接するブロックのみ

                        Block neighbor = currentBlock.getRelative(dx, dy, dz);

                        // 固体ブロックであり、かつすでに検出済みでないかチェック
                        if (neighbor.getType().isSolid() && !shipBlocks.contains(neighbor)) {
                            shipBlocks.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }
            }
        }

        player.sendMessage("§a乗り物を検出しました！ブロック数: " + shipBlocks.size());

        // Shipオブジェクトを生成し、リストに保存するなど
        // ここから移動ロジックに繋がる
        // new Ship(shipBlocks, player);
    }
}
