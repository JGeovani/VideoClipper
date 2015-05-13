package com.simas.vc;

/**
 * Created by Simas Abramovas on 2015 May 03.
 */

import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ToDo each class that uses this, should specify when the delay ends.

/**
 * Message Handler class that queues messages until {@code resume} is called.
 */
public class DelayedHandler {

	private final List<Runnable> mRunnableQueue = Collections
			.synchronizedList(new ArrayList<Runnable>());
	private boolean mResumed;
	private Handler mHandler;

	/**
	 * Create a resumable handler. Default state: Paused.
	 * @param handler    {@code Handler} to run the messages
	 */
	public DelayedHandler(Handler handler) {
		mHandler = handler;
	}

	/**
	 * Resume the handler.
	 */
	public final synchronized void resume() {
		mResumed = true;
		while (mRunnableQueue.size() > 0) {
			final Runnable runnable = mRunnableQueue.get(0);
			mRunnableQueue.remove(0);
			mHandler.post(runnable);
		}
	}

	/**
	 * Adds the Runnable to the queue. It will be run immediately if the handler is resumed.
	 */
	public void add(Runnable runnable) {
		if (mResumed) {
			mHandler.post(runnable);
		} else {
			mRunnableQueue.add(runnable);
		}
	}

}